<template>
  <v-row>
    <v-col align="center" justify="center">
      <v-container>
        <v-row>
          <v-col>
            <v-card>
              <v-card-title justify="center">
                <h5>Search</h5>
              </v-card-title>
              <v-card-text>
                <v-row>
                  <v-col cols="6">
                    <v-text-field label="Field" type="text"></v-text-field>
                  </v-col>
                  <v-col cols="6">
                    <v-text-field label="Value" type="text"></v-text-field>
                  </v-col>
                </v-row>
                <v-row>
                  <v-col>
                    <v-btn small color="green white--text">Search</v-btn>
                  </v-col>
                </v-row>
              </v-card-text>
            </v-card>
          </v-col>
        </v-row>
        <v-row>
          <v-col>
            <v-data-table
              :headers="headers"
              :items="items"
              hide-default-footer
              class="elevation-1"
              style="overflow: auto"
            >
              <template v-slot:item.actions="{ item }">
                <v-btn icon>
                  <v-icon color="green darken-2">mdi-download</v-icon>
                </v-btn>
              </template>
            </v-data-table>
          </v-col>
        </v-row>
      </v-container>
    </v-col>
  </v-row>
</template>

<script>
import axios from "axios";

export default {
  name: "Home",
  data() {
    return {
      headers: [
        {
          text: "Title",
          sortable: true,
          value: "title",
        },
        {
          text: "Writer",
          sortable: true,
          value: "writer",
        },
        {
          text: "Genres",
          sortable: false,
          value: "genres",
        },
        {
          text: "Key Words",
          sortable: false,
          value: "keywords",
        },
        { text: "Download", value: "actions", sortable: false },
      ],
      items: [],
    };
  },
  mounted() {
    axios
      .get("/book")
      .then((response) => {
        console.log(response.data.content);
        this.items = response.data.content;
      })
      .catch((error) => {});
  },
};
</script>
