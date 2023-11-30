<template>
  <div class="">
    <div class="body container">
      <div class="profile-body">
        <div class="personal-data">
          <label class="profile-title">Personal Data</label>
          <UserData :user="user" @updateUserInfo="getUserInfo" @checkTicket="checkTicket"/>
        </div>
        <div class="personal-card">
          <label class="profile-title">Library Card</label>
          <p v-if="!isHaveTicket">You haven't created an electronic reader ticket. Please fill in all personal data fields to create a ticket.</p>
          <LibraryCard v-else :user-info="user" :address="address"/>
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
import { sendRequest } from "@/scripts/request";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Profile",
  components: {LibraryCard, UserOrders, UserData},
  data() {
    return {
      user: {},
      address: {},
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
        this.address = this.user.address ? JSON.parse(this.user.address) : "";
      }
    }
  }
}
</script>

<style scoped>

.body {
  display: flex;
}
.profile-body {
  display: flex;
  flex-direction: column;
  gap: 4em;
  width: 100%;
  padding: 2em;
}

.profile-body > div {
  display: flex;
  flex-direction: column;
  gap: 1.5em;
}

.profile-title {
  color: var(--blue-opacity);
  font-size: 16pt;
  font-style: normal;
  font-weight: 500;
  line-height: normal;
}

</style>