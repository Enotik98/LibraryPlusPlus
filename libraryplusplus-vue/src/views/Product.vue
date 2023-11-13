<template>
  <Header/>
  <div class="container">
    {{ $route.params.id }}
  </div>
</template>

<script>
import {sendRequest} from "@/scripts/request";
import Header from "@/components/Header.vue";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Product",
  components: {Header},
  data() {
    return {
      book: {}
    }
  },
  mounted() {
    this.getBook();
  },
  methods: {
    async getBook() {
      try {
        const response = await sendRequest("/book/" + this.$route.params.id, "GET", null);
        if (response.ok) {
          this.book = await response.json()
          console.log(this.book)
        }
      } catch (e) {
        console.error(e)
      }
    }
  }
}
</script>

<style scoped>

</style>