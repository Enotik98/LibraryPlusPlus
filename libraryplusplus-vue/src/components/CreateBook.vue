<template>
  <div class="book">
    <form class="form-book" @submit="creatBook">
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
        <input class="form-control" type="text" v-model="book.about">
      </div>
      <div class="form-group">
        <label class="form-label">Path image</label>
        <input class="form-control" type="text" v-model="book.path_img">
      </div>
      <div class="button">
        <button type="submit" class="btn btn-outline-dark">Save</button>
      </div>
    </form>
  </div>
</template>

<script>
import {sendRequest} from "@/scripts/request";

export default {
  name: "CreateBook",
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
  methods: {
    async creatBook(){
      try{
        const response = await sendRequest("/book", "POST", this.book)
        if (response.ok){
          console.log(response.text);
        }
      }catch (e){
        console.error(e)
      }
    }
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