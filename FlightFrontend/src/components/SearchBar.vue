<template>
  <div class="min-h-screen bg-white text-black p-6">
    <div class="flex justify-between items-center mb-10">
      <div class="flex items-center space-x-2">
        <img
          src="../assets/Easytrip_logo.png"
          alt="Tripadvisor"
          class="w-25 h-25"
        />
      </div>
      <div class="flex items-center space-x-4 text-sm">
        <a href="#" class="hover:underline">Discover</a>
        <a href="#" class="hover:underline">Trips</a>
        <a href="#" class="hover:underline">Review</a>
        <a href="#" class="hover:underline">More</a>
        <span>🌐 USD</span>
        <img
          src="https://via.placeholder.com/32"
          alt="Profile"
          class="w-8 h-8 rounded-full"
        />
      </div>
    </div>

    <h1 class="text-4xl font-bold text-center mb-6">Where to?</h1>

    <div class="flex justify-center space-x-6 text-gray-700 mb-4">
      <div
        :class="[
          'cursor-pointer',
          selectedTab === 'cities'
            ? 'border-b-2 border-black pb-1 font-semibold'
            : '',
        ]"
        @click="setSelectedTab('cities')"
      >
        Cities
      </div>
      <div
        :class="[
          'cursor-pointer',
          selectedTab === 'attractions'
            ? 'border-b-2 border-black pb-1 font-semibold'
            : '',
        ]"
        @click="setSelectedTab('attractions')"
      >
        Attractions
      </div>
      <div
        :class="[
          'cursor-pointer',
          selectedTab === 'flights'
            ? 'border-b-2 border-black pb-1 font-semibold'
            : '',
        ]"
        @click="setSelectedTab('flights')"
      >
        Flights
      </div>
      <div
        :class="[
          'cursor-pointer',
          selectedTab === 'add'
            ? 'border-b-2 border-black pb-1 font-semibold'
            : '',
        ]"
        @click="openAddModal"
      >
        Add Attraction
      </div>
    </div>

    <div class="flex justify-center mt-6">
      <div
        class="flex w-full max-w-xl shadow-md rounded-full border border-gray-200 overflow-hidden"
      >
        <input
          v-model="searchQuery"
          type="text"
          placeholder="Places to go, things to do, hotels..."
          class="w-full px-6 py-3 focus:outline-none"
        />
        <button
          class="bg-emerald-400 text-white px-6 py-3 font-semibold hover:bg-emerald-500"
          @click="searchCities"
        >
          Search
        </button>
      </div>
    </div>

    <!-- Attraction Cards -->
    <div
      v-if="selectedTab === 'attractions' && results.length"
      class="mt-8 max-w-6xl mx-auto"
    >
      <h2 class="text-xl font-bold mb-4">Attractions:</h2>
      <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
        <div
          v-for="attraction in results"
          :key="attraction.id"
          class="border rounded-2xl shadow-md overflow-hidden flex flex-col items-center justify-start p-3 bg-white w-[280px] min-h-[380px] mx-auto"
        >
          <div class="text-base font-semibold mb-2 text-center">
            {{ attraction.name }}
          </div>
          <div v-if="attraction.photoUrl" class="mb-2">
            <img
              :src="attraction.photoUrl"
              alt="Attraction photo"
              class="w-[260px] h-[160px] object-cover rounded-lg"
            />
          </div>
          <div class="text-sm text-gray-600 text-center mb-4">
            {{ attraction.description }}
          </div>
          <div class="flex justify-center space-x-4 mt-auto mb-2">
            <button
              @click="editAttraction(attraction)"
              class="bg-blue-600 text-white px-3 py-1 rounded hover:bg-blue-700 text-sm"
            >
              Edit
            </button>
            <button
              @click="confirmDeleteAttraction(attraction.id)"
              class="bg-red-600 text-white px-3 py-1 rounded hover:bg-red-700 text-sm"
            >
              Delete
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";
import Swal from "sweetalert2";

const searchQuery = ref("");
const results = ref([]);
const selectedTab = ref("cities");
const cities = ref([]); // 加這一行來儲存所有城市

const apiPaths = {
  cities: "http://localhost:8080/cities/city",
  attractions: "http://localhost:8080/attractions",
  flights: "http://localhost:8080/flights",
};

const setSelectedTab = (tab) => {
  selectedTab.value = tab;
};

const openAddModal = () => {
  addAttraction(); // 修正這一行：執行函數
};

const fetchAllCities = async () => {
  try {
    const res = await axios.get("http://localhost:8080/cities");
    cities.value = res.data;
  } catch (err) {
    console.error("Failed to fetch cities", err);
  }
};

const searchCities = async () => {
  if (!searchQuery.value) return;

  try {
    const query = encodeURIComponent(searchQuery.value.trim());
    const url = `${apiPaths[selectedTab.value]}/${query}`;
    const response = await axios.get(url);

    if (selectedTab.value === "attractions") {
      const attractions = await Promise.all(
        response.data.map(async (attr) => {
          try {
            const photoRes = await axios.get(
              `http://localhost:8080/photos/attraction/${attr.id}`
            );
            const relativeUrl = photoRes.data[0]?.url || "";
            attr.photoUrl = relativeUrl
              ? `http://localhost:8080${relativeUrl}`
              : "";
          } catch {
            attr.photoUrl = "";
          }
          return attr;
        })
      );
      results.value = attractions;
    } else {
      results.value = response.data;
    }
  } catch (error) {
    console.error("Error fetching data:", error);
  }
};

const addAttraction = () => {
  let cityOptions = "";
  cities.value.forEach((city) => {
    cityOptions += `<option value="${city.id}">${city.name}</option>`;
  });

  Swal.fire({
    title: "Add Attraction",
    html: `
      <input id="name" class="swal2-input" placeholder="Name">
      <input id="description" class="swal2-input" placeholder="Description">
      <input id="address" class="swal2-input" placeholder="Address">
      <input id="latitude" class="swal2-input" placeholder="Latitude">
      <input id="longitude" class="swal2-input" placeholder="Longitude">
      <input id="rating" class="swal2-input" placeholder="Rating">
      <input id="category" class="swal2-input" placeholder="Category (comma separated)">
      <select id="cityId" class="swal2-select">${cityOptions}</select>
    `,
    confirmButtonText: "Create",
    cancelButtonText: "Cancel",
    showCancelButton: true,
    preConfirm: async () => {
      const name = document.getElementById("name").value;
      const description = document.getElementById("description").value;
      const address = document.getElementById("address").value;
      const latitude = parseFloat(document.getElementById("latitude").value);
      const longitude = parseFloat(document.getElementById("longitude").value);
      const rating = parseFloat(document.getElementById("rating").value);
      const category = document
        .getElementById("category")
        .value.split(",")
        .map((c) => c.trim());
      const cityId = parseInt(document.getElementById("cityId").value);

      try {
        await axios.post("http://localhost:8080/attractions", {
          name,
          description,
          address,
          latitude,
          longitude,
          rating,
          category,
          cityId,
        });
        await searchCities();
      } catch (err) {
        console.error("Add failed", err);
        Swal.showValidationMessage("Failed to add attraction");
      }
    },
  });
};

const editAttraction = (attraction) => {
  let cityOptions = "";
  cities.value.forEach((city) => {
    const selected = city.id === attraction.city.id ? "selected" : "";
    cityOptions += `<option value="${city.id}" ${selected}>${city.name}</option>`;
  });

  Swal.fire({
    title: "Edit Attraction",
    html: `
      <input id="name" class="swal2-input" placeholder="Name" value="${
        attraction.name
      }">
      <input id="description" class="swal2-input" placeholder="Description" value="${
        attraction.description
      }">
      <input id="address" class="swal2-input" placeholder="Address" value="${
        attraction.address
      }">
      <input id="latitude" class="swal2-input" placeholder="Latitude" value="${
        attraction.latitude
      }">
      <input id="longitude" class="swal2-input" placeholder="Longitude" value="${
        attraction.longitude
      }">
      <input id="rating" class="swal2-input" placeholder="Rating" value="${
        attraction.rating
      }">
      <input id="category" class="swal2-input" placeholder="Category (comma separated)" value="${attraction.category?.join(
        ", "
      )}">
      <select id="cityId" class="swal2-select">${cityOptions}</select>
    `,
    confirmButtonText: "Save",
    cancelButtonText: "Cancel",
    showCancelButton: true,
    customClass: {
      confirmButton: "bg-blue-600 text-white px-4 py-2 rounded",
      cancelButton: "bg-gray-500 text-white px-4 py-2 rounded ml-2",
    },
    preConfirm: async () => {
      const name = document.getElementById("name").value;
      const description = document.getElementById("description").value;
      const address = document.getElementById("address").value;
      const latitude = parseFloat(document.getElementById("latitude").value);
      const longitude = parseFloat(document.getElementById("longitude").value);
      const rating = parseFloat(document.getElementById("rating").value);
      const category = document
        .getElementById("category")
        .value.split(",")
        .map((c) => c.trim());
      const cityId = parseInt(document.getElementById("cityId").value);

      try {
        await axios.put(`http://localhost:8080/attractions/${attraction.id}`, {
          name,
          description,
          address,
          latitude,
          longitude,
          rating,
          category,
          cityId,
        });
        await searchCities();
      } catch (err) {
        console.error("Update failed", err);
        Swal.showValidationMessage("Failed to update attraction");
      }
    },
  });
};

const confirmDeleteAttraction = (id) => {
  Swal.fire({
    title: "Are you sure?",
    text: "You won't be able to revert this!",
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Yes, delete it!",
    cancelButtonText: "Cancel",
    customClass: {
      confirmButton: "bg-red-600 text-white px-4 py-2 rounded",
      cancelButton: "bg-gray-600 text-white px-4 py-2 rounded ml-2",
    },
  }).then(async (result) => {
    if (result.isConfirmed) {
      try {
        await axios.delete(`http://localhost:8080/attractions/${id}`);
        await searchCities();
      } catch (err) {
        console.error("Delete failed", err);
      }
    }
  });
};

onMounted(fetchAllCities);
</script>

<style scoped>
.aspect-square {
  aspect-ratio: 1 / 1;
}
</style>
