<template>
  <Header v-if="showHeader" />
  <router-view class="main-body"/>
  <Info v-if="showHeader"/>
</template>
<script>
export default {
  computed: {
    showHeader() {
      return this.$route.path !== '/login';
    }
  },
  mounted() {
    this.$store.dispatch('fetchUser');
  }
}
</script>

<style>
:root {
  --blue: #011AFB;
  --background-blue: rgba(54, 74, 255, 0.10);
  --blue-opacity: rgba(54, 74, 255, 0.75);

  --slider-connect-bg: #000;
  --slider-tooltip-bg: #000;
  --slider-handle-ring-color: #000;

  height: 100vh;
  font-size: 12pt;
}
html, body {
  margin: 0;
  padding: 0;
  height: 100vh;
}
.main-body {
  padding-bottom: 5em;
}
.multiselect__tag {
  background: #000000 !important;
}
.multiselect__tag-icon::after {
 color: #ffffff !important;
}
.multiselect__option--highlight {
  background: #000000 !important;
}
.multiselect__option--highlight::after {
  background: #000000 !important;

}
.title {
  color: var(--blue-opacity);
  font-size: 32pt;
  font-style: normal;
  font-weight: 500;
  line-height: normal;
}
#app {
  font-family: Inter, sans-serif;
  /*font-family: Avenir, Helvetica, Arial, sans-serif;*/
  -webkit-font-smoothing: antialiased;
  /*height: 100vh;*/
  /*-moz-osx-font-smoothing: grayscale;*/
  /*text-align: center;*/
  /*color: #2c3e50;*/
}

.btn {
  white-space: nowrap;
}

</style>
<script setup>
import Header from "@/components/Header.vue";
import Info from "@/components/InfoIcon.vue";
</script>