<template>
  <div class="search-criteria">
    <div class="criteria-title">
      <input v-model="params.title" class="form-control" placeholder="Search">
    </div>
    <div class="check-lost form-check" v-if="!isUser">
      <input v-model="params.lostBook" class="form-check-input" type="checkbox" id="lostBook">
      <label class="form-check-label" for="lostBook">Lost Book</label>
    </div>
    <div class="criteria-year">
      <label>Year</label>
      <div class="range-year">
        <input v-model="params.minYear" class="form-control" placeholder="From">
        <input v-model="params.maxYear" class="form-control" placeholder="To">
      </div>
    </div>
    <div class="criteria-author">
      <label>Author</label>
      <input v-model="params.author" class="form-control" placeholder="Author">
    </div>
    <div class="criteria-genre">
      <label>Genre</label>
<!--      <div>-->
<!--        <VueMultiselect v-model="multiSelectValue" :options="options"></VueMultiselect>-->
<!--      </div>-->
      <select v-model="params.genre" class="form-select">
        <option value="comedy">Comedy</option>
        <option value="fantasy">Fantasy</option>
      </select>
    </div>
    <div>
      <button @click="clearFilter" class="btn btn-outline-dark">Clear Filter</button>
    </div>
  </div>
</template>

<script>
import {mapState} from "vuex";
// import VueMultiselect from 'vue-multiselect';

export default {
  name: "SearchCriteria",
  // components: {VueMultiselect},
  props: {
    searchParams: Object
  },
  data() {
    return {
      params: {
        title: "",
        minYear: 1990,
        maxYear: new Date().getFullYear(),
        author: "",
        genre: "",
        lostBook: false,
      },
      multiSelectValue: null,
      options: ['Comedy', 'Drama', 'Horror', 'Fantasy']
    }
  },
  computed: {
    ...mapState(['isUser'])
  },
  watch: {
    params: {
      handler(newParams) {
        this.$emit('apply-filters', newParams)
      },
      deep: true
    }
  },
  methods: {
    clearFilter() {
      for (let key in this.params) {
        this.params[key] = "";
      }
    }
    // sendSearchCriteria() {
    //   //update searchParams on HomeView
    //   this.$emit('updateCriteria', {...this.params});
    // }
  }
}
</script>
<style src="vue-multiselect/dist/vue-multiselect.min.css"></style>

<style scoped>
.search-criteria > div {
  margin-bottom: 1em;
}

.range-year {
  display: flex;
  justify-content: space-between;
}
</style>