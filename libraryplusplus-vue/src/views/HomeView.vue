<template>
  <div class="body container">
    <div class="search-criteria">
      <SearchCriteria @updateCriteria="updateCriteria"/>
    </div>
    <div class="list-books">
      <div class="card-grid">
        <div v-for="book in books" :key="book.id" class="card-item">
          <CardBook :book="book"/>
        </div>
      </div>
    </div>
    <div class="admin-panel" v-if="!isUser">
      <button @click="openModal" class="btn btn-outline-dark">Add Book</button>
    </div>
  </div>
  <ModalWindow ref="ModalWindow">
    <CreateBook/>
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
      searchParams: {
        title: "",
        minYear: 1990,
        maxYear: new Date().getFullYear(),
        author: "",
        genre: "",
        lostBook: false,
      },
      book: {
        id: '',
        title: '',
        author: '',
        genre: '',
        isbn: '',
        publication_year: '',
        quantity: '',
        about: '',
        add_date: ''
      },
      test_book: {
        id: '1',
        title: 'Harry Potter ',
        author: 'Alex Bilok',
        genre: 'Fantasy',
        isbn: '99999',
        publication_year: '2022',
        quantity: '2',
        about: 'text',
        add_date: '2023-10-22'
      },
      books: []
    }
  },
  computed: {
    ...mapState(['isUser'])
  },
  mounted() {
    this.getBooks();
  },
  methods: {
    openModal() {
      this.$refs.ModalWindow.openModal();
    },
    updateCriteria(newCriteria) {
      this.searchParams = {...newCriteria};
    },
    async getBooks() {
      try {
        const response = await sendRequest("/book", "GET", null);
        if (response.ok) {
          const data = await response.json();
          this.books = data;
          console.log(this.books)
        }
      } catch (e) {
        console.error(e)
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
  width: 21rem;
}

.admin-panel {
  padding: 1em;
}

.list-books {
  display: flex;
  justify-content: center;

}

.card-grid {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  width: 100%;

}

.card-item {
  max-width: 18rem;
  /*opacity: 0.5;*/
  margin: 1rem;
  box-shadow: 0px 0px 4px 2px rgba(0, 0, 0, 0.25);
  border-radius: 0.5rem;
}


</style>
