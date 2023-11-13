<template>
  <Header />
<div class="container">
{{$route.params.id}}
</div>
</template>

<script>
import Header from "@/components/Header.vue";
import {sendRequest} from "@/scripts/request";

export default {
  name: "OrderInfo",
  components: {Header},
  data() {
    return {
      order: {}
    }
  },
  mounted() {
    this.getOrder();
  },
  methods: {
    async getOrder() {
      try {
        const response = await sendRequest("/order/" + this.$route.params.id, "GET", null);
        if (response.ok){
          this.order = await response.json();
          console.log(this.order)
        }
      }catch (e){
        console.error(e)
      }
    }
  }
}
</script>

<style scoped>

</style>