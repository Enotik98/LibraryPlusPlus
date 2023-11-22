<template>
  <div class="">
    <div class="body container">
      <div class="profile-menu">
        <div class="menu-item">
          <svg class="icon">
            <use href="../assets/user.svg#user"/>
          </svg>
          <span>Personal Data</span>
        </div>
        <div class="menu-item">
          <svg class="icon">
            <use xlink:href="../assets/Card.svg#Card" style="vertical-align: center"/>
          </svg>
          <span>Library Card</span>
        </div>
        <div class="menu-item">
          <svg class="icon">
            <use href="../assets/list.svg#list"/>
          </svg>
          <span>My Orders</span>
        </div>
      </div>
      <div class="profile-body">
        <div class="personal-data">
          <label class="profile-title">Personal Data</label>
          <UserData :user="user" @updateUserInfo="getUserInfo" @checkTicket="checkTicket"/>
        </div>
        <div class="personal-card" v-show="isHaveTicket">
          <label class="profile-title">Library Card</label>
          <p v-if="!isHaveTicket">You haven't created an electronic reader ticket. Please fill in all personal data fields to create a ticket.</p>
          <LibraryCard v-else :user-info="user"/>
        </div>
        <div class="personal-orders">
          <label class="profile-title">My Orders</label>
          <UserOrders />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import UserData from "@/components/UserData.vue";
import UserOrders from "@/components/UserOrders.vue";
import LibraryCard from "@/components/LibraryCard.vue";
import {sendRequest} from "@/scripts/request";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Profile",
  components: {LibraryCard, UserOrders, UserData},
  data() {
    return {
      user: {},
      isHaveTicket: true,
    }
  },
  mounted() {
    this.getUserInfo();
  },
  methods: {
    checkTicket(val) {
      this.isHaveTicket = val;
    },
    async getUserInfo(){
      const response = await sendRequest("/user/profile", "GET", null);
      if (response.ok) {
        this.user = await response.json();
        console.log(this.user)
      }
    }
  }
}
</script>

<style scoped>

.body {
  display: flex;
}

.profile-menu {
  padding: 1em;
  width: 20em;
  overflow-y: auto;
  border-right: 1px solid rgba(1, 26, 251, 0.35);

}

.profile-body {
  width: 100%;
  padding: 1.5em;
}

.menu-item {
  display: flex;
  align-items: center;

  padding: 0.5rem .7em;
  margin-bottom: 1em;
  border-radius: .25rem;
  color: var(--blue);
  background-color: var(--background-blue);
}

.icon {
  width: 2em;
  height: 1.7rem;

  fill: none;
}

.profile-title {
  color: var(--blue-opacity);
  font-size: 1.5rem;
  font-style: normal;
  font-weight: 500;
  line-height: normal;
}

</style>