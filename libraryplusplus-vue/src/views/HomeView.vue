<template>
  <div class="body">
    <div class="search-criteria">
      <SearchCriteria @apply-filters="applyFilterBook"/>
    </div>
    <div class="list-books">
      <div class="card-grid" v-if="showLostBook">
        <div v-for="lost in lostBooks" :key="lost.id" class="card-item">
          <CardBook :book="lost.book" style="opacity: .5;"/>
        </div>
      </div>
      <div class="card-grid">
        <div v-for="book in filterBooks" :key="book.id" class="card-item">
          <CardBook :book="book"/>
        </div>
      </div>
    </div>
    <div class="admin-panel" v-if="!isUser">
      <button @click="openModal" class="btn btn-outline-dark button">Add Book</button>
    </div>
  </div>
  <ModalWindow ref="ModalWindow">
    <CreateBook :modal-close="() => {this.$refs.ModalWindow.closeModal()}"/>
  </ModalWindow>
</template>

<script>
// @ is an alias to /src
import ModalWindow from "@/components/ModalWindow.vue";
import CreateBook from "@/components/CreateBook.vue";
import {sendRequest} from "@/scripts/request";
import CardBook from "@/components/CardBook.vue";
import SearchCriteria from "@/components/SearchCriteria.vue";
import {mapState} from "vuex";

export default {
  name: 'HomeView',
  components: {
    SearchCriteria,
    CardBook,
    CreateBook,
    ModalWindow,
  },
  data() {
    return {
      books: [],
      lostBooks: [],
      filterBooks: [],
      showLostBook: false
    }
  },
  computed: {
    ...mapState(['isUser'])
  },
  mounted() {
    this.getBooks();
    if (!this.isUser) {
      this.getLostBook();
    }
  },
  methods: {
    openModal() {
      this.$refs.ModalWindow.openModal();
    },
    applyFilterBook(params) {
      let result = this.books;
      this.showLostBook = params.lostBook
      if (params.title || params.year[0] || params.year[1] || params.author || params.genre.length > 0) {
        console.log(params.genre)
        result = result.filter(book => {
          const includesGenre = params.genre.length === 0 || params.genre.some(genre => (!genre || book.genre.toLowerCase().includes(genre.toLowerCase())));
          console.log(includesGenre)
          return (
              (!params.title || book.title.toLowerCase().includes(params.title.toLowerCase())) &&
              (!params.author || book.author.toLowerCase().includes(params.author.toLowerCase())) &&
              includesGenre &&
              (!params.year[0] || book.publication_year >= params.year[0]) &&
              (!params.year[1] || book.publication_year <= params.year[1])
          )
        })
      }
      this.filterBooks = result.sort((a, b) => new Date(b.add_date) - new Date(a.add_date))
    },
    async getLostBook() {
      const response = await sendRequest("/book/lostBook", "GET", null);
      if (!response.ok) {
        console.error(await response.text())
        return;
      }
      this.lostBooks = await response.json();
      this.lostBooks = this.lostBooks.sort((a, b) => new Date(b.dateLost) - new Date(a.dateLost))
    },
    async getBooks() {
        const response = await sendRequest("/book", "GET", null);
        if (response.ok) {
          const data = await response.json();
          this.books = data.sort((a, b) => new Date(b.add_date) - new Date(a.add_date));
          this.filterBooks = this.books;
        }else {
          console.error(await response.json())
        }
      }
    }
}
</script>
<style scoped>
.body {
  display: flex;
}

.search-criteria {
  padding: 1em;
  flex-basis: 24em;
  flex-shrink: 0;
}

.admin-panel {
  padding: 1em;
  flex-basis: 24em;
  flex-shrink: 0;
}

.list-books {
  flex-grow: 1;
}

.card-grid {
  justify-content: center;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, max-content));
  gap: 2em;
}

.card-item {
  max-width: 18rem;
  /*opacity: 0.5;*/
  margin: 1rem;
  box-shadow: 0px 0px 4px 2px rgba(0, 0, 0, 0.25);
  border-radius: 0.5rem;
}


</style>
