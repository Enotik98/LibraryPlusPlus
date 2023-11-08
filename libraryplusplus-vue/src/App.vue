<template>
  <router-link to="/registration" />
  <router-view/>
</template>

<style>
:root {
  --blue: #011AFB;
  --background-blue: rgba(54, 74, 255, 0.10);
  --blue-opacity: rgba(54, 74, 255, 0.75);

  height: 100vh;
}
html, body {
  margin: 0;
  padding: 0;
  height: 100vh;
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
</style>
