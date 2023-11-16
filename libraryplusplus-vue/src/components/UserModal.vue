<template>
<div class="user">
  <div>
    <p>Email: {{userInfo.email}}</p>
    <p>First Name: {{userInfo.first_name}}</p>
    <p>Last Name: {{userInfo.last_name}}</p>
    <p>Phone: {{userInfo.phone}}</p>
    <p>Address: {{userInfo.address}}</p>
    <p>Role: {{userInfo.role}}</p>
    <p>Restrictions</p>
    <div class="form-check">
      <input type="checkbox" class="form-check-input" v-model="isSanctions">
      <label>Sanctions</label>
    </div>
    <div class="form-check">
      <input type="checkbox" class="form-check-input" v-model="isBlocked">
      <label>Blocked</label>
    </div>
  </div>

</div>
</template>

<script>
export default {
  name: "UserModal",
  props: {
    userInfo: Object
  },
  data() {
    return {
      isSanctions: false,
      isBlocked: false,
    }
  },
  mounted() {
    this.isSanctions = this.userInfo.isSanctions;
    this.isBlocked = this.userInfo.isBlocked;

  }
}
</script>

<style scoped>
.user {
  width: 30em;
}
</style>