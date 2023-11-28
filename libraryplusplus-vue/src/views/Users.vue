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
        <th>Restrictions</th>
      </tr>
      </thead>
      <tbody>
      <tr>
        <td class="td-id"><input type="text" class="form-control" placeholder="#" v-model="filterUser.id" ></td>
        <td><input type="text" class="form-control" placeholder="Email" v-model="filterUser.email" ></td>
        <td><input type="text" class="form-control" placeholder="First Name" v-model="filterUser.first_name" ></td>
        <td><input type="text" class="form-control" placeholder="Last Name" v-model="filterUser.last_name" ></td>
        <td><input type="text" class="form-control" placeholder="Phone" v-model="filterUser.phone"></td>
        <td><input type="checkbox" class="form-check-input" v-model="filterUser.restrictions"></td>
      </tr>
      <tr v-for="user in filteredUser" :key="user.id" @click="openModal(user)">
        <td>{{ user.id }}</td>
        <td>{{ user.email }}</td>
        <td>{{ user.first_name }}</td>
        <td>{{ user.last_name }}</td>
        <td>{{user.phone}}</td>
        <td>
          <i v-if="(user.isSanctions || user.isBlocked)" class="fa-solid fa-circle-xmark" style="color: #ff8c82; font-size: 1.3rem"></i>
          <i v-else class="fa-solid fa-circle-check" style="color: #96d35f; font-size: 1.3rem"></i>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
  <ModalWindow ref="ModalWindow">
    <UserModal :user-info="chooseUser" :close-modal-window="() => {this.$refs.ModalWindow.closeModal()}" :get-users="getUsers"/>
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
        restrictions: false,
      }
    }
  },
  mounted() {
    this.getUsers()
  },
  computed: {
    filteredUser() {
      let result = this.users;
      if (!this.filterUser.id && !this.filterUser.email && !this.filterUser.first_name && !this.filterUser.last_name && !this.filterUser.phone && !this.filterUser.restrictions) {
        return this.users;
      }
      result = result.filter(user => {
        return (
            (!this.filterUser.id || user.id.toString().includes(this.filterUser.id)) &&
            (!this.filterUser.email || (user.email && user.email.toLowerCase().includes(this.filterUser.email.toLowerCase()))) &&
            (!this.filterUser.first_name || (user.first_name && user.first_name.toLowerCase().includes(this.filterUser.first_name.toLowerCase()))) &&
            (!this.filterUser.last_name || (user.last_name && user.last_name.toLowerCase().includes(this.filterUser.last_name.toLowerCase()))) &&
            (!this.filterUser.phone || (user.phone && user.phone.toLowerCase().includes(this.filterUser.phone.toLowerCase()))) &&
            (!this.filterUser.restrictions || (user.isBlocked || user.isSanctions))
        );
      });
      return this.sort(result);
    }
  },
  methods: {
    openModal(data) {
      this.chooseUser = data;
      this.$refs.ModalWindow.openModal();
    },
    sort(arrayUser) {
      return arrayUser.sort((a, b) => {
        const role = {"USER": 1, "ADMIN": 2, "LIBRARIAN": 3};

        const roleComparison = role[a.role] - role[b.role];
        if (roleComparison === 0) {
          return a.email.localeCompare(b.email);
        }
        return roleComparison;
      });
    },
    async getUsers(){
      try {
        const response = await sendRequest("/user", "GET", null);
        if (!response.ok){
          console.log("error")
        }
        this.users = await response.json();
        this.users = this.sort(this.users);
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
.td-id {
  width: 6em;
}
.form-check-input {
  font-size: 1.6rem;
}
</style>