<!-- eslint-disable vue/max-attributes-per-line -->
<template>
  <v-layout>
    <v-navigation-drawer v-model="drawer" expand-on-hover rail>
      <v-list density="compact" item-props :items="items" nav />

      <template #append>
        <v-list-item
          class="ma-2"
          link
          nav
          prepend-icon="mdi-cog-outline"
          title="Settings"
        />
      </template>
    </v-navigation-drawer>

    <v-app-bar border="b" class="ps-4" flat>
      <v-app-bar-nav-icon
        v-if="$vuetify.display.smAndDown"
        @click="drawer = !drawer"
      />

      <v-app-bar-title>Flight Management System</v-app-bar-title>

      <template #append>
        <v-btn class="text-none me-2" height="48" icon slim>
          <v-avatar color="surface-light" image="" size="32" />

          <v-menu activator="parent">
            <v-list density="compact" nav>
              <v-list-item
                append-icon="mdi-cog-outline"
                link
                title="Settings"
              />

              <v-list-item append-icon="mdi-logout" link title="Logout" />
            </v-list>
          </v-menu>
        </v-btn>
      </template>
    </v-app-bar>

    <v-main>
      <div class="pa-4">
        <v-sheet
          border="dashed md"
          color="surface-light"
          height="500"
          rounded="lg"
          width="100%"
        />
      </div>
    </v-main>
  </v-layout>
</template>

<script setup>
import { ref } from "vue";

const drawer = ref(true);

const items = ref([
  {
    title: "Flights",
    prependIcon: "mdi mdi-airplane-takeoff",
    link: true,
  },
  {
    title: "Members",
    prependIcon: "mdi-account-group",
    link: true,
  },
  {
    title: "Orders",
    prependIcon: "mdi mdi-invoice-text-edit-outline",
    link: true,
  },
  {
    title: "Travels",
    prependIcon: "mdi mdi-island",
    link: true,
  },
  {
    title: "Rewards",
    prependIcon: "mdi mdi-gift-open-outline",
    link: true,
  },
]);
</script>
