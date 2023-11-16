<template>
  <div class="container">
    <table class="table table-hover">
      <thead>
      <tr>
        <th>#</th>
        <th>Email</th>
        <th>First Name</th>
        <th>Last Nam</th>
        <th>Phone</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="user in users" :key="user.id" @click="openModal(user)">
        <td>{{ user.id }}</td>
        <td>{{ user.email }}</td>
        <td>{{ user.first_name }}</td>
        <td>{{ user.last_name }}</td>
        <td>{{user.phone}}</td>
      </tr>
      </tbody>
    </table>
  </div>
  <ModalWindow ref="ModalWindow">
    <UserModal :user-info="chooseUser"/>
  </ModalWindow>
</template>

<script>
import {sendRequest} from "@/scripts/request";
import ModalWindow from "@/components/ModalWindow.vue";
import UserModal from "@/components/UserModal.vue";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Users",
  components: {UserModal, ModalWindow},
  data() {
    return {
      users: [],
      chooseUser: null
    }
  },
  mounted() {
    this.getUsers()
  },
  methods: {
    openModal(data) {
      this.chooseUser = data;
      this.$refs.ModalWindow.openModal();
    },
    async getUsers(){
      try {
        const response = await sendRequest("/user", "GET", null);
        if (!response.ok){
          console.log("error")
        }
        this.users = await response.json();
        this.chooseUser = this.users[0];
        console.log(this.users)
      }catch (e) {
        console.error(e)
      }
    }
  }
}
</script>

<style scoped>

</style>