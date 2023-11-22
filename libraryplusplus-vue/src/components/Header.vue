<template>
<header class="">
  <nav class="navbar">
    <span><router-link to="/" class="logo">LibraryPlusPlus</router-link></span>
    <ul>
      <li><router-link to="/" class="link" exact><div class="nav-item" >Catalog</div></router-link></li>
      <li v-if="!isUser"><router-link to="/orders" class="link" exact><div class="nav-item">Orders</div></router-link></li>
      <li v-if="!isUser"><router-link to="/users" class="link" exact><div class="nav-item">Users</div></router-link></li>
      <li v-if="userRole === 'ADMIN'"><router-link to="/analytics" class="link" exact><div class="nav-item">Analytics</div></router-link></li>
      <li v-if="isLoggedIn"><router-link to="/profile" class="link" exact><div class="nav-item" >Profile</div></router-link></li>
      <li v-if="!isLoggedIn"><router-link to="/login" class="link" exact><div class="nav-item" >Sing in</div></router-link></li>
      <li v-else><div @click="logoutSys" class="link"><div class="nav-item" >Log out</div></div></li>
    </ul>
  </nav>
</header>
</template>

<script>

import {mapMutations, mapState} from "vuex";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "header",
  data() {
    return {}
  },
  computed: {
    ...mapState(['isUser', "isLoggedIn", "userRole"])
  },
  methods: {
    logoutSys() {
      this.logout();
      this.$router.push('/');
    },
    ...mapMutations(['logout'])
  }
}
</script>

<style scoped>
header {
  width: 100%;
}
.navbar {
  display: flex;
  justify-content: space-between;
  padding: 1em 2em;
  box-shadow: 0px 4px 4px 0px #00000040;

}

.navbar ul {
  list-style: none;
  display: flex;
  gap: 1em;
  margin: 0;
}
.navbar ul li {
  position: relative;
  width: 6em;
}
.nav-item{
  padding: .3rem;
  width: 6em;
  text-align: center;
  border-radius: 5px;


  background-color: #ffffff;
  color: black;
}
.link.router-link-exact-active .nav-item,
.nav-item:hover{
  background-color: black;
  color: white;
}

.logo {
  text-decoration: none;
  color: var(--blue);

  font-family: Megrim;
  font-size: 32px;
  font-weight: 500;
  line-height: 37px;
  letter-spacing: 0em;
  text-align: left;
}

.link {
  text-decoration: none;
}
</style>