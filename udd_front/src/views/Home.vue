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
                  <v-col cols="3">
                    <v-text-field
                      v-model="search.title"
                      label="Title"
                      type="text"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="3">
                    <v-text-field
                      v-model="search.writer"
                      label="Writer"
                      type="text"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="3">
                    <v-text-field
                      v-model="search.genres"
                      label="Genres"
                      type="text"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="3">
                    <v-text-field
                      v-model="search.keyWords"
                      label="Key Words"
                      type="text"
                    ></v-text-field>
                  </v-col>
                </v-row>
                <v-row align="center" justify="center">
                  <v-col cols="3">
                    <v-select
                      v-model="search.operatorTitle"
                      :items="operator"
                      label="Operator for Title"
                      outlined
                      dense
                    ></v-select>
                  </v-col>
                  <v-col cols="3">
                    <v-select
                      v-model="search.operatorWriter"
                      :items="operator"
                      label="Operator for Writer"
                      outlined
                      dense
                    ></v-select>
                  </v-col>
                  <v-col cols="3">
                    <v-select
                      v-model="search.operatorGenres"
                      :items="operator"
                      label="Operator for Genres"
                      outlined
                      dense
                    ></v-select>
                  </v-col>
                  <v-col cols="3">
                    <v-select
                      v-model="search.operatorKeyWords"
                      :items="operator"
                      label="Operator for Key Words"
                      outlined
                      dense
                    ></v-select>
                  </v-col>
                </v-row>
                <v-row align="center" justify="center">
                  <v-col cols="7">
                    <v-textarea
                      v-model="search.text"
                      label="Text"
                      outlined
                      dense
                    ></v-textarea>
                  </v-col>
                  <v-col cols="3">
                    <v-select
                      v-model="search.operatorText"
                      :items="operator"
                      label="Operator for Text"
                      outlined
                      dense
                    ></v-select>
                  </v-col>
                </v-row>
                <v-row align="center" justify="center">
                  <v-col cols="6">
                    <v-select
                      v-model="search.searchType"
                      :items="searchType"
                      label="Type of Search"
                      dense
                    ></v-select>
                  </v-col>
                </v-row>
                <v-row>
                  <v-col>
                    <v-btn small color="green white--text" @click="searchBook"
                      >Search</v-btn
                    >
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
                <v-btn icon @click="downloadBook(item)">
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
      operator: ["AND", "OR"],
      searchType: ["regular", "phrase"],
      search: {
        title: "",
        operatorTitle: "",
        writer: "",
        operatorWriter: "",
        genres: "",
        operatorGenres: "",
        keyWords: "",
        operatorKeyWords: "",
        text: "",
        operatorText: "",
        searchType: "phrase",
      },
    };
  },
  methods: {
    searchBook() {
      axios
        .post("/book/search", this.search)
        .then((response) => {
          this.items = response.data.content;
        })
        .catch((error) => {});
    },
    downloadBook(item) {
      let url = item.filename
      let formData = new FormData();
      formData.append("url", url);
      axios
        .post("/book/download", formData)
        .then((response) => {
          let blob = new Blob([response.data], { type: "application/pdf" });
          let link = document.createElement("a");
          link.href = window.URL.createObjectURL(blob);
          link.download = item.title+".pdf";
          link.click();
          console.log(response.data);
        })
        .catch((error) => {});
    },
  },
  mounted() {
    axios
      .get("/book")
      .then((response) => {
        this.items = response.data.content;
      })
      .catch((error) => {});
  },
};
</script>
