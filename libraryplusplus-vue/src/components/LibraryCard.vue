<template>
  <div class="card">
    <div class="card_header">
      <span class="logo">LibraryPlusPlus</span>
      <div class="vertical"></div>
      <div class="library-contact">
        <div>
          <svg class="icon">
            <use xlink:href="../assets/planet.svg#planet" ></use>
          </svg>
          <span>www.library-plus-plus.com</span>
        </div>
        <div>
          <svg class="icon">
            <use xlink:href="../assets/Phone.svg#Phone" ></use>
          </svg>
          <span>+380 (73) 234 45 54</span>
        </div>
      </div>
    </div>
    <div class="card_body">
      <div class="card_qr">
        <svg class="icon qr">
          <use xlink:href="../assets/qr-code.svg#Group"></use>
          ;
        </svg>
      </div>
      <div class="card_content">
        <span class="name">{{userInfo.first_name}} {{userInfo.last_name}}</span>
        <span class="info">{{userInfo.email}}</span>
        <span class="info">+38{{userInfo.phone}}</span>
        <span class="address">St.{{userInfo.address}}</span>
      </div>
    </div>
  </div>
  <div class="card_btn">
    <button class="btn btn-outline-primary">Download Card</button>
  </div>
</template>

<script>
export default {
  name: "LibraryCard",
  props: {
    userInfo: Object
  }
}
</script>

<style scoped>
.card {
  padding: 1em;

  font-weight: 500;
}

.card_header {
  display: flex;
  align-items: center;
  padding-bottom: 1em;
}

.logo {
  color: var(--blue);

  font-family: Megrim;
  font-size: 32px;
  line-height: 37px;
  letter-spacing: 0em;
  text-align: left;
}

.vertical {
  background-color: rgba(1, 26, 251, 0.35);
  height: 2rem;
  width: 0.0625rem;
  margin: 0 2em;
}

.library-contact,
.card_content {
  display: flex;
  flex-direction: column;
}

.icon {
  fill: none;
}

.library-contact > div > .icon {
  width: 1.5em;
  height: 1em;
}
.name {
  color: var(--blue-opacity);
  font-size: 2rem;
}
.info {
  color: var(--blue);
  opacity: .35;
}
.address {
  padding-top: 2em;
}

.icon.qr{
  height: 17em;
}

.card_body {
  display: flex;
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