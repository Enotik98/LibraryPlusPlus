<template>
  <div class="book">
    <form class="form-book" @submit.prevent="submitFormBook">
      <label v-if="!editBook" class="title-card">Add Book</label>
      <label v-else class="title-card">Edit Book</label>
      <div class="form-group">
        <label >Image</label>
        <textarea class="form-control" type="text" v-model="book.path_img" rows="1" placeholder="Image Url"/>
      </div>
      <div>
        <label>Title</label>
        <input class="form-control" type="text" v-model="book.title" placeholder="Title">
      </div>
      <div class="form-group">
        <label>Author</label>
        <input class="form-control" type="text" v-model="book.author" placeholder="Author">
      </div>
      <div class="form-group">
        <label>Genre</label>
        <input class="form-control" type="text" v-model="book.genre" placeholder="Genre">
      </div>
      <div class="book-input-group">
        <div class="form-group pe-1">
          <label>ISBN</label>
          <input class="form-control" type="text" v-model="book.isbn" placeholder="ISNB">
        </div>
        <div class="form-group px-1">
          <label>Publication Year</label>
          <input class="form-control" type="text" v-model="book.publication_year" placeholder="Publication Year">
        </div>
        <div class="form-group ps-1">
          <label>Quantity</label>
          <input class="form-control" type="text" v-model="book.quantity" placeholder="Quantity">
        </div>
      </div>
      <div class="form-group">
        <label>About</label>
        <textarea class="form-control" type="text" v-model="book.about" rows="7" placeholder="About"/>
      </div>
      <div class="button">
        <button v-if="!editBook" type="submit" class="btn btn-outline-dark">Add Book</button>
        <button v-else type="submit" class="btn btn-outline-dark">Save Book</button>
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
  padding: 0 1em;
  color: #000;
  /*font-family: Inter,serif;*/
  font-size: 16px;
  font-style: normal;
  font-weight: 400;
  line-height: normal;

}

.title-card {
  font-size: 1.2em;
}
.book-input-group {
  display: flex;
}

.form-group {
  margin: .5em 0;
}

.form-group > label {
  padding-bottom: .4em;
}

.button {
  display: flex;
  justify-content: end;
}

.button button {
  width: 10em;
}
</style>