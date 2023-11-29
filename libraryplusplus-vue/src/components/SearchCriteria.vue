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
      <div class="year">
        <div class="year__slider">
        <Slider v-model="params.year" :min="1500" :max="2023" />
        </div>
        <div class="range-year">
          <input v-model="params.year[0]" class="form-control" placeholder="From">
          <input v-model="params.year[1]" class="form-control" placeholder="To">
        </div>
      </div>
    </div>
    <div class="criteria-author">
      <label>Author</label>
      <input v-model="params.author" class="form-control" placeholder="Author">
    </div>
    <div class="criteria-genre">
      <label>Genre</label>
      <div>
        <VueMultiselect v-model="params.genre" :options="options" :multiple="true"
                        placeholder="Select Genre"></VueMultiselect>
      </div>
    </div>
    <div>
      <button @click="clearFilter" class="btn btn-outline-dark">Clear Filter</button>
    </div>
  </div>
</template>

<script>
import {mapState} from "vuex";
import VueMultiselect from 'vue-multiselect';
import Slider from "@vueform/slider";
import {optionsGenre} from "@/scripts/utils";

export default {
  name: "SearchCriteria",
  components: {VueMultiselect, Slider},
  props: {
    searchParams: Object
  },
  data() {
    return {
      params: {
        title: "",
        year: [1500, new Date().getFullYear()],
        minYear: 1500,
        maxYear: new Date().getFullYear(),
        author: "",
        lostBook: false,
        genre: [],
      },
      options: optionsGenre.sort((a, b) => a.localeCompare(b)),
    }
  },
  computed: {
    ...mapState(['isUser'])
  },
  watch: {
    params: {
      handler(newParams) {
        console.log(this.params.year)
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
      this.params.year = [1500, new Date().getFullYear()]
    },
  }
}
</script>
<style src="vue-multiselect/dist/vue-multiselect.css"></style>
<style src="@vueform/slider/themes/default.css"/>

<style scoped>
.search-criteria > div {
  margin-bottom: 1em;
}

.year__slider {
  margin-top: 3em;
  margin-bottom: .9em;
}

.range-year {
  display: flex;
  justify-content: space-between;
  gap: 3em;
}
</style>