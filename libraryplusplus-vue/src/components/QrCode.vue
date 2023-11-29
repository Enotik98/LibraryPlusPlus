<template>
    <div v-if="user_id">
     <QRCodeVue3
            :width="256"
            :height="256"
            :value="uri"
            :qrOptions="{ typeNumber: 0, mode: 'Byte', errorCorrectionLevel: 'Q' }"
            :imageOptions="{ hideBackgroundDots: true, imageSize: 0.1, margin: 0 }"
            :dotsOptions="{
              type: 'classy',
              color: '#000',
            }"
            :cornersSquareOptions="{ type: 'extra-rounded', color: '#000' }"
            :cornersDotOptions="{ type: 'dot', color: '#000000' }"
            fileExt="png"
            :download="false"
            myclass="my-qur"
            imgclass="img-qr"

          />
    </div>
  </template>
  
  <script>
  import QRCodeVue3 from "qrcode-vue3";
  
  export default {
    name: 'QrCode',
    props: {
        user_id: Number
    },
    computed: {
        uri() {
            const { protocol, host } = window.location;
            return  protocol + '//' + host + "/profile/" + this.user_id;
        }
    },
    components: {
      QRCodeVue3
    },
  }
  </script>