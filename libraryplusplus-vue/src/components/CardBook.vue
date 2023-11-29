<template>
  <div class="card ">
    <img :src="book.path_img" class="card-img-top img" :alt="'Book' + book.id">
    <div class="card-body">
      <div class="card_header">
        <span class="card_title">{{ book.title }}</span>
        <span class="">{{ book.author }}</span>
      </div>
      <div class="card_text">
        <span class="card_year">{{ book.publication_year }}</span>
        <span class=""> {{ book.genre }}</span>
      </div>
      <div class="card_footer">
        <router-link :to="{name: 'Product', params: {id: book.id}}" >More information</router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "CardBook",
  props: {
    book: Object
  }
}
</script>

<style scoped>
.card {
  height: 100%;
  position: relative;
}
.img {
  width: 100%;
  height: 20em;
  object-fit: cover;
  object-position: center top;
}

.card-body {
  display: flex;
  flex-direction: column;
  padding: .5em;
  position: relative;
}

.card_header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
}

.card_title {
  font-size: 16pt;
}

.card_text {
  flex-grow: 1;
  color: rgba(0, 0, 0, 0.50);
}

.card_year {
  padding-right: 1em;
}

.card_footer {
  display: flex;
  justify-content: flex-end;
  margin-top: auto;
  font-size: 10pt;
  align-items: flex-end;
  /*bottom: 0;*/
}
</style>