<template>
  <div class="container mt-5">
    <div class="d-flex justify-content-center align-items-center">
      <form @submit.prevent="submitForm">
        <div class="row mb-2">
          <label class="col-4">First name</label>
          <input type="text" class="col-8" v-model="formData.first_name">
        </div>
        <div class="row mb-2">
          <label class="col-4">Phone</label>
          <input type="text" class="col-8" v-model="formData.phone">
        </div>
        <div class="row">
          <label class="col-4">Password</label>
          <input class="col-8" type="text" v-model="formData.password">
        </div>
        <button type="submit">Submit</button>
      </form>
    </div>
  </div>
</template>

<script>
import {sendRequest} from "@/scripts/request";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Registration",
  data() {
    return {
      formData : {
        first_name: '',
        phone: '',
        password: ''
      }
    };
  },
  methods: {
    async submitForm(){
      try {
        const response = await sendRequest('/user/registration', 'POST', this.formData)
        if (response.ok){
          console.log('ok')
        }
      }catch (error){
        console.error(error);
      }
    }
  }

}
</script>

<style scoped>
form{
  padding: 1em 2em;
  border: solid black 1px;
  border-radius: 5px;
}

</style>