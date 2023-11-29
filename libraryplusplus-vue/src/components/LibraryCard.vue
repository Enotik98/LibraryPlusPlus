<template>
  <div class="card printable">
    <div class="card_header">

      <img src="../assets/Logo.svg"/>
      <div class="vertical"></div>
      <div class="library-contact">
        <div>
          <img src="../assets/planet.svg"/>
          <span>www.library-plus-plus.com</span>
        </div>
        <div>
          <img src="../assets/Phone.svg"/>
          <span>+380 (73) 234 45 54</span>
        </div>
      </div>
    </div>
    <div class="card_body">
      <div class="card_qr">
        <QrCode :user_id="userInfo.id"/>
      </div>
      <div class="card_content">
        <span class="name">{{userInfo.first_name}} {{userInfo.last_name}}</span>
        <span class="info">{{userInfo.email}}</span>
        <span class="info">+38{{userInfo.phone}}</span>
        <span class="address">{{address.city}}, St.{{address.street}} {{address.houseNumber}}, {{address.apartmentNumber}}</span>
      </div>
    </div>
  </div>
  <div class="card_btn">
    <button class="btn btn-outline-primary" @click="() => {downloadCard(userInfo)}">Download Card</button>
  </div>
</template>

<script>

import QrCode from './QrCode.vue'

export default {
  name: "LibraryCard",
  components: {QrCode},
  props: {
    userInfo: Object,
    address: Object
  },
  computable: {
    
  },
  methods: {
    downloadCard(user) {
      const card = document.querySelector('.printable');

      window.domtoimage.toJpeg(card).then((dataURL) => {
        const downloadLink = document.createElement('a');
        downloadLink.href = dataURL;
        downloadLink.download = user.first_name + '_' + user.last_name + '_libraryCard.jpeg';

        // Trigger the download
        downloadLink.click();
      })
    }
  },
  data() {
    return {

    }
  },
  mounted() {
  }
}
</script>

<style scoped>
.card {
  padding: 1.5em;
  font-weight: 500;
}

.card_header {
  display: flex;
  align-items: center;
  padding-bottom: 1em;
}

.vertical {
  background-color: rgba(1, 26, 251, 0.35);
  height: 2rem;
  width: 1px;
  margin: 0 1.5em;
}
.card_content {
  display: flex;
  flex-direction: column;
}

.library-contact > div {
  display: flex;
  gap: .5em;
}
.name {
  color: var(--blue-opacity);
  font-size: 24pt;
}
.info {
  color: var(--blue);
  opacity: .35;
}
.address {
  padding-top: 2em;
}
.card_body {
  display: flex;
  gap: 3em;
}
.card_btn {
  display: flex;
  justify-content: end;
  padding: 1em 0;
}
.card_btn > button {
  width: 15em;
  color: var(--blue);
}

</style>