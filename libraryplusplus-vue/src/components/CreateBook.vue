<template>
  <div class="book">
    <form class="form-book" @submit.prevent="submitFormBook">
      <label class="title-card">Create Book</label>
      <div class="form-group">
        <label class="form-label">Title</label>
        <input class="form-control" type="text" v-model="book.title">
      </div>
      <div class="form-group">
        <label class="form-label">Author</label>
        <input class="form-control" type="text" v-model="book.author">
      </div>
      <div class="form-group">
        <label class="form-label">Genre</label>
        <input class="form-control" type="text" v-model="book.genre">
      </div>
      <div class="form-group">
        <label class="form-label">isbn</label>
        <input class="form-control" type="text" v-model="book.isbn">
      </div>
      <div class="form-group">
        <label class="form-label">Year</label>
        <input class="form-control" type="text" v-model="book.publication_year">
      </div>
      <div class="form-group">
        <label class="form-label">Quantity</label>
        <input class="form-control" type="text" v-model="book.quantity">
      </div>
      <div class="form-group">
        <label class="form-label">About</label>
        <textarea class="form-control" type="text" v-model="book.about" rows="4"/>
      </div>
      <div class="form-group">
        <label class="form-label">Path image</label>
        <textarea class="form-control" type="text" v-model="book.path_img" rows="2"/>
      </div>
      <div class="button">
        <button v-if="!editBook" type="submit" class="btn btn-outline-dark">Create</button>
        <button v-else type="submit" class="btn btn-outline-dark">Save</button>
      </div>
    </form>
  </div>
</template>

<script>
import {sendRequest} from "@/scripts/request";

export default {
  name: "CreateBook",
  props: {
    editBook: Object,
    modalClose: Function,
  },
  data() {
    return {
      book: {
        path_img: null,
        title: "",
        author: "",
        genre: "",
        isbn: "",
        publication_year: "",
        quantity: "",
        about: "",
      }
    }
  },
  mounted() {
    if (this.editBook != null) {
      this.book = this.editBook
    }
  },
  methods: {
    extractFileIdFromUrl(urlGoogle) {
      const match = urlGoogle.match(/\/d\/([^/]+)|[?&]id=([^&]+)/);
      if (match) {
        return match[1] || match[2];
      }
      return "";
    },
    async submitFormBook() {
      let errorMessage = null;
      if (this.book.path_img !== "") {
        const file = this.extractFileIdFromUrl(this.book.path_img)
        this.book.path_img = "https://drive.google.com/uc?export=view&id=" + file
        console.log(this.book.path_img)
      }
      if (!this.editBook) {
        const response = await sendRequest("/book", "POST", this.book)
        if (!response.ok) {
          errorMessage = await response.text()
        }
      } else {
        const response = await sendRequest("/book", "PUT", this.book)
        if (!response.ok) {
          errorMessage = await response.text()
        }
      }
      errorMessage ? this.$Notiflix.Notify.failure(errorMessage) : this.$Notiflix.Notify.success("Successful!")
      !errorMessage ? this.modalClose() : null;

    },
  }
}
</script>

<style scoped>
.book {
  width: 40em;
  padding: 0 2em;
}

.title-card {
  width: 100%;
  text-align: center;
}

.form-group {
  margin: 1em 0;
}

.button {
  display: flex;
  justify-content: center;
}

.button button {
  width: 10em;
}
</style>