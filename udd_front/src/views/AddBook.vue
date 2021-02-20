<template>
  <v-row>
    <v-col align="center" justify="center">
      <v-snackbar
        v-model="alert.show"
        :timeout="alert.timeout"
        :color="alert.color"
        top
        right
        style="margin-top: 2%"
        >{{ alert.text }}</v-snackbar
      >
      <v-form
        v-model="form.valid"
        method="POST"
        enctype="multipart/form-data"
        id="fileUploadForm"
        ref="form"
      >
        <v-card style="width: 70%; margin-top: 15%">
          <v-card-title justify="center">
            <v-col>
              <h4>Add book</h4>
            </v-col>
          </v-card-title>
          <v-card-text>
            <v-row>
              <v-col cols="12">
                <v-text-field
                  v-model="book.title"
                  :rules="form.rules.title"
                  label="Title"
                  type="text"
                  name="title"
                ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field
                  v-model="book.keywords"
                  :rules="form.rules.keywords"
                  label="Key Words"
                  type="text"
                  name="keywords"
                ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-select
                  :items="genres"
                  label="Genre"
                  multiple
                  v-model="book.genres"
                  :rules="form.rules.genres"
                  name="genres"
                ></v-select>
              </v-col>
              <v-col cols="12">
                <v-file-input
                  v-model="book.files"
                  :rules="form.rules.files"
                  label="Files"
                  name="files"
                />
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <v-btn
                  type="submit"
                  color="green white--text"
                  @click.prevent="addBook"
                  >Add</v-btn
                >
              </v-col>
            </v-row>
          </v-card-text>
        </v-card>
      </v-form>
    </v-col>
  </v-row>
</template>

<script>
import axios from "axios";
import { mapGetters } from "vuex";
export default {
  data: () => ({
    book: {
      title: null,
      keywords: null,
      genres: null,
      files: null,
    },
    form: {
      valid: false,
      rules: {
        title: [(v) => !!v || "Title is required"],
        keywords: [(v) => !!v || "Key words is required"],
        genres: [(v) => !!v || "Genre is required"],
        files: [(v) => !!v || "File is required"],
      },
    },
    alert: {
      show: false,
      text: "Error",
      timeout: 2000,
      color: "red",
    },
    genres: ["Action", "Comedy", "Mistery"],
  }),
  computed: {
    ...mapGetters(["user"]),
  },
  methods: {
    addBook() {
      if (this.form.valid) {
        let formData = new FormData();
        Object.keys(this.book).forEach((key) => {
          formData.append(key, this.book[key]);
        });
        formData.append("writer", this.user.username);
        axios
          .post("/book/add", formData)
          .then((response) => {
            this.$refs.form.reset()
            this.alert.color = "green";
            this.alert.text = response.data;
            this.alert.show = true;
          })
          .catch((error) => {
            this.alert.text = error.response.data;
            this.alert.color = "red";
            this.alert.show = true;
          });
      } else {
        this.alert.color = "red";
        this.alert.text = "All fields are required.";
        this.alert.show = true;
      }
    },
  },
};
</script>

<style>
</style>