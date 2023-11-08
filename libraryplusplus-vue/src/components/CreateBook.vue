<template>
  <div class="book">
    <form class="form-book">
      <label class="title-card">Create Book</label>
      <div class="form-group">
        <label class="form-label">Title</label>
        <input class="form-control" type="text" v-model="book.title">
      </div>
      <div class="form-group">
        <label class="form-label">Author</label>
        <input class="form-control" type="text" v-model="book.author">
      </div>
      <div class="button">
        <button class="btn btn-outline-dark">Save</button>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  name: "CreateBook",
  data() {
    return {
      book: {
        title: "",
        author: ""
      }
    }
  }
}
</script>

<style scoped>
.book {
  width: 50em;
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