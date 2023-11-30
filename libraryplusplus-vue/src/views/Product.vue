<template>
  <div class="container d-flex justify-content-center ">
    <div class="back-link" @click="$router.go(-1)"><i class="fa-solid fa-chevron-left" style="color: #000000;"></i><span>Back</span></div>
    <div class="product">
      <!--            <img src="../assets/samuray.jpg" class="card-img-left" alt="Book">-->
      <img :src="book.path_img" class="card-img-left" alt="Book">
      <div class="product_body">
        <span class="card-title">{{ book.title }}</span>
        <div class="card-info">
          <span class="card-text">Author: {{ book.author }}</span>
          <span class="card-text">Genre: {{ book.genre }}</span>
          <span class="card-text">Year: {{ book.publication_year }}</span>
          <span class="card-text">ISBN: {{ book.isbn }}</span>
        </div>
        <span class="card-about">{{ book.about }}</span>
        <div class="order-control">
          <div :class="!isAvailable ? 'disabled' : ''">
            <label>Order</label>
            <select class="form-select" v-model="selectDays" name="order-duration" :disabled="!isAvailable"
                    :class="!isAvailable ? 'disabled' : ''">
              <option :value="7">1 week</option>
              <option :value="14">2 week</option>
              <option :value="21">3 week</option>
              <option :value="30">1 month</option>
            </select>
          </div>
          <button class="btn btn-outline-dark" @click="createOrder" name="order" :disabled="!isAvailable">Add to Cart</button>
          <div v-if="!isAvailable" class="form-text warning">The book isn't available. Please try to order later</div>
          <div v-if="isSanctions" class="form-text warning">You account has a sanctions. For details information, contact with the
            administrator. </div>
        </div>
      </div>
    </div>
    <div class="edit-book" v-if="!isUser">
      <button class="btn btn-outline-dark mb-1" @click="openModal">Edit</button>
      <button class="btn btn-outline-danger" @click="deleteBook">Delete</button>
    </div>
    <ModalWindow ref="ModalWindow">
      <CreateBook :edit-book="book" :modalClose="() => {this.$refs.ModalWindow.closeModal()}"/>
    </ModalWindow>
    <!--    {{ $route.params.id }}-->
  </div>
</template>

<script>
import {sendRequest} from "@/scripts/request";
// import Header from "@/components/Header.vue";
import {calculateDate} from "@/scripts/utils";
import {mapState} from "vuex";
import ModalWindow from "@/components/ModalWindow.vue";
import CreateBook from "@/components/CreateBook.vue";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Product",
  components: {CreateBook, ModalWindow},
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
    ...mapState(['isUser', 'isLoggedIn', "isSanctions"])
  },
  mounted() {
    this.getBook();
  },
  methods: {
    openModal() {
      this.$refs.ModalWindow.openModal();
    },
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
          this.$Notiflix.Notify.failure(this.errorMess)
        }
        return;
      }
      this.$Notiflix.Notify.success("The book has been added!")
    },
    async deleteBook() {
      const response = await sendRequest("/book", "DELETE", {id: this.book.id});
      if (!response.ok) {
        this.errorMess = await response.text();
        this.$Notiflix.Notify.failure(this.errorMess);
        return
      }
      this.$Notiflix.Notify.success("Deleted successful!");
      this.$router.go(-1);
    }
  }
}
</script>

<style scoped>

.container {
  display: flex;
  gap: 1em;
}
.back-link {
  display: flex;
  height: fit-content;
  align-items: center;
  padding: 1.5em 0;
}

.product {
  padding: 1.5em 0;
  display: grid;
  grid-template-columns: 300px auto;
  gap: 2em;
}

.product > img {
  width: 100%;
  object-fit: cover;
  object-position: center top;
  border-radius: 8px;
  box-shadow: 0 0 4px 0 rgba(0, 0, 0, 0.25);
}

.product_body {
  display: flex;
  flex-direction: column;
  width: 100%;
}
.card-title {
  font-size: 2em;
  margin-bottom: .5em;
}

.card-info {
  display: flex;
  flex-direction: column;
  margin-bottom: 2em;
}
.card-text {
  color: rgba(0, 0, 0, 0.5)
}

.card-about {
  margin-bottom: 2em;
}
.order-control {
  width: 70%;
}

.order-control label {
  font-size: 1.5em;
  margin-bottom: 1em;
}
.order-control > button {
  margin-top: 1em;
  width: 70%;
}

[name=order-duration] {
  margin-bottom: 2em;
}

.warning {
  color: #ff4013;
}

.disabled {
  opacity: .7;
}

.edit-book {
  padding: 1.5em 0 ;
  display: flex;
  gap: 1em;
}

.edit-book > button {
  height: fit-content;
  width: 5em;
}
</style>