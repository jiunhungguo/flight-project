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
      class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mt-10 px-4"
    >
      <div
        v-for="attraction in results"
        :key="attraction.id"
        class="border rounded-lg shadow p-4 flex flex-col justify-between items-center aspect-square"
      >
        <h3 class="text-lg font-bold mb-2 text-center">
          {{ attraction.name }}
        </h3>

        <img
          v-if="attraction.photos && attraction.photos.length"
          :src="attraction.photos[0].url"
          alt="Attraction Photo"
          class="w-full h-48 object-cover rounded mb-3"
        />

        <p class="text-sm text-gray-600 text-center flex-1">
          {{ attraction.description }}
        </p>

        <div class="flex justify-center space-x-4 mt-4">
          <button
            @click="editAttraction(attraction)"
            class="bg-blue-500 text-white px-3 py-1 rounded hover:bg-blue-600"
          >
            Edit
          </button>
          <button
            @click="confirmDelete(attraction.id)"
            class="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600"
          >
            Delete
          </button>
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

const apiPaths = {
  cities: "http://localhost:8080/cities/city",
  attractions: "http://localhost:8080/attractions",
  flights: "http://localhost:8080/flights",
};

const setSelectedTab = (tab) => {
  selectedTab.value = tab;
};

const openAddModal = () => {
  setSelectedTab("add");
  // 這裡你可以加入 SweetAlert2 彈窗邏輯來新增 Attraction
};

const searchCities = async () => {
  if (!searchQuery.value) return;

  try {
    const query = encodeURIComponent(searchQuery.value.trim());
    const url = `${apiPaths[selectedTab.value]}/${query}`;
    const response = await axios.get(url);

    if (selectedTab.value === "attractions") {
      // 加載照片資訊
      const attractions = await Promise.all(
        response.data.map(async (attr) => {
          try {
            const photoRes = await axios.get(
              `http://localhost:8080/photos/attraction/${attr.id}`
            );
            attr.photos = photoRes.data;
          } catch (e) {
            attr.photos = [];
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

const editAttraction = (attraction) => {
  Swal.fire({
    title: "Edit Attraction",
    html:
      `<input id="name" class="swal2-input" placeholder="Name" value="${attraction.name}">` +
      `<input id="description" class="swal2-input" placeholder="Description" value="${attraction.description}">` +
      `<input id="address" class="swal2-input" placeholder="Address" value="${attraction.address}">`,
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

      try {
        await axios.put(`http://localhost:8080/attractions/${attraction.id}`, {
          name,
          description,
          address,
          latitude: attraction.latitude,
          longitude: attraction.longitude,
          rating: attraction.rating,
          category: attraction.category,
          cityId: attraction.city.id, // 確保包含 cityId
        });
        searchCities();
      } catch (err) {
        console.error("Update failed", err);
        Swal.showValidationMessage("Failed to update attraction");
      }
    },
  });
};

const confirmDelete = (id) => {
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
        searchCities();
      } catch (err) {
        console.error("Delete failed", err);
      }
    }
  });
};
</script>

<style scoped>
.aspect-square {
  aspect-ratio: 1 / 1;
}
</style>
