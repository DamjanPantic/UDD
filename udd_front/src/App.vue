<template>
  <v-app>
    <v-app-bar app color="primary" dark>
      <router-link to="/" tag="h3">Literalno udruzenje</router-link>

      <v-spacer></v-spacer>

      <v-menu offset-y v-if="user != null">
        <template v-slot:activator="{ on, attrs }">
          <v-btn icon class="mr-2" v-bind="attrs" v-on="on">
            <v-icon>mdi-reorder-horizontal</v-icon>
          </v-btn>
        </template>
        <v-list>
          <v-list-item to="/add-book">
            Add Book
          </v-list-item>
        </v-list>
      </v-menu>

      <v-btn to="/login" icon class="mr-2" v-if="user == null">
        <v-icon>mdi-account</v-icon>
      </v-btn>

      <v-btn @click="logout" icon class="mr-2" v-if="user != null">
        <v-icon>mdi-logout</v-icon>
      </v-btn>
    </v-app-bar>

    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script>
import { mapGetters, mapMutations } from "vuex";

export default {
  name: "App",
  data: () => ({}),
  computed: {
    ...mapGetters(["user"]),
  },
  methods: {
    ...mapMutations(["setUser"]),
    logout() {
      this.setUser(null);
      this.$router.push("/login");
    },
  },
};
</script>

<style scoped>
a:hover {
  text-decoration: none;
}

h3:hover {
  cursor: pointer;
}
</style>
