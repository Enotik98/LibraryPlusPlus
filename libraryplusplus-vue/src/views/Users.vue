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
      <tr >
        <td><input type="text" class="form-control" placeholder="#" v-model="filterUser.id" ></td>
        <td><input type="text" class="form-control" placeholder="Email" v-model="filterUser.email" ></td>
        <td><input type="text" class="form-control" placeholder="First Name" v-model="filterUser.first_name" ></td>
        <td><input type="text" class="form-control" placeholder="Last Name" v-model="filterUser.last_name" ></td>
        <td><input type="text" class="form-control" placeholder="Phone" v-model="filterUser.phone"></td>
      </tr>
      <tr v-for="user in filteredUser" :key="user.id" @click="openModal(user)">
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
      chooseUser: null,
      filterUser: {
        id: "",
        email: "",
        first_name: "",
        last_name: "",
        phone: "",
      }
    }
  },
  mounted() {
    this.getUsers()
  },
  computed: {
    filteredUser() {
      if (!this.filterUser.id && !this.filterUser.email && !this.filterUser.first_name && !this.filterUser.last_name && !this.filterUser.phone) {
        return this.users;
      }
      return this.users.filter(user => {
        return (
            (!this.filterUser.id || user.id.toString().includes(this.filterUser.id)) &&
            (!this.filterUser.email || (user.email && user.email.toLowerCase().includes(this.filterUser.email.toLowerCase()))) &&
            (!this.filterUser.first_name || (user.first_name && user.first_name.toLowerCase().includes(this.filterUser.first_name.toLowerCase()))) &&
            (!this.filterUser.last_name || (user.last_name && user.last_name.toLowerCase().includes(this.filterUser.last_name.toLowerCase()))) &&
            (!this.filterUser.phone || (user.phone && user.phone.toLowerCase().includes(this.filterUser.phone.toLowerCase())))
        );
      });
    }
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