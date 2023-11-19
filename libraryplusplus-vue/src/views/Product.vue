<template>
  <div class="container d-flex justify-content-center">
    <div class="product">
      <img src="../assets/samuray.jpg" class="card-img-left" alt="Book">
      <div class="product_body">
        <span class="card-title">{{ book.title }}</span>
        <span class="card-text">Author: {{ book.author }}</span>
        <span class="card-text">Genre: {{ book.genre }}</span>
        <span class="card-text">Year: {{ book.publication_year }}</span>
        <span class="card-text">ISBN: {{ book.isbn }}</span>
        <span class="card-text">{{ book.about }}</span>
        <div>
          <p>How long</p>
          <select class="form-select" v-model="selectDays" :disabled="!isAvailable">
            <option :value="7">1 week</option>
            <option :value="14">2 week</option>
            <option :value="21">3 week</option>
            <option :value="30">1 month</option>
          </select>
        </div>
        <button class="btn btn-outline-dark" @click="createOrder" :disabled="!isAvailable">Add to Cart</button>
        <div>{{ errorMess }}</div>
      </div>
    </div>
    <div class="edit-book" v-if="!isUser">
      <button class="btn btn-outline-dark">Edit</button>
    </div>
    <!--    {{ $route.params.id }}-->
  </div>
</template>

<script>
import {sendRequest} from "@/scripts/request";
// import Header from "@/components/Header.vue";
import {calculateDate} from "@/scripts/utils";
import {mapState} from "vuex";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Product",
  components: {},
  data() {
    return {
      book: {},
      selectDays: 7,
      order: {
        book_id: "",
        return_date: "",
      },
      isAvailable: false,
      errorMess: null,
    }
  },
  computed: {
    ...mapState(['isUser', 'isLoggedIn'])
  },
  mounted() {
    this.getBook();
  },
  methods: {
    async getBook() {
      try {
        const response = await sendRequest("/book/" + this.$route.params.id, "GET", null);
        if (response.ok) {
          const data = await response.json();
          this.book = data['book'];
          this.isAvailable = data['isAvailable'];
          console.log(data)
        }
      } catch (e) {
        console.error(e)
      }
    },
    async createOrder() {
      if (!this.isLoggedIn) this.$router.push('/login')
      this.order.book_id = this.$route.params.id
      this.order.return_date = calculateDate(new Date, this.selectDays);
      if (this.order.book_id === "" || this.order.return_date === "") {
        return;
      }
      console.log(this.order)
      const response = await sendRequest("/order", "POST", this.order);
      if (!response.ok) {
        if (response.status === 403) {
          this.errorMess = await response.text();
        }
        console.log("error")
      }
    }
  }
}
</script>

<style scoped>
.product {
  width: 40em;
  /*padding: 1em;*/
  margin-top: 3em;
  border: 1px solid rgba(0, 0, 0, 0.176);
  border-radius: 6px;
  display: flex;
}

.product > img {
  width: 40%;
  height: 25em;
  object-fit: cover;
  border-radius: 6px 0 0 6px;
}

.product_body {
  padding: 1em;
  display: flex;
  flex-direction: column;
  width: 100%;
}

.product_body > span {
  padding: .3em 0;
}

.product_body > div {
  padding: .6em 0;
}

.card-title {
  font-size: 1.7em;
}

.edit-book {
  margin-top: 3em;
  padding-left: 1em;
}

.edit-book > button {
  width: 5em;
}
</style>