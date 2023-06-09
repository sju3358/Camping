<template>
  <div>
    <table>
      <tr>
        <th>캠핑장 번호</th>
        <th>캠핑장명</th>
        <th>위치</th>
        <th>즐겨찾기 수</th>
      </tr>
      <tr>
        <td>{{ camp.content_id }}</td>
        <td>{{ camp.title }}</td>
        <td>{{ camp.addr }}</td>
        <td>{{ camp.count_favorites }}</td>
      </tr>
    </table>
    <br />
    <br />
    <br />
    <img :src="`${this.camp.img}`" alt="관광지 이미지" />
    <div class="overview">
      <h1>내용</h1>
      {{ camp.overview }}
      <br />
      <button
        @click="addFavorites"
        v-show="this.campFavoritesFlag == 0"
        v-if="$store.state.userIdx != -1">
        즐겨찾기
      </button>
      <button @click="removeFavorites" v-show="this.campFavoritesFlag == 1">
        즐겨찾기 해제
      </button>
    </div>
  </div>
</template>

<script>
import http from "@/util/http.js";

export default {
  data() {
    return {
      camp: {
        content_id: 0,
        title: "",
        addr: "",
        sido_code: 0,
        gugun_code: 0,
        latitude: 0,
        longtitude: 0,
        img: "",
        overview: "",
      },
      campFavoritesFlag: 0,
    };
  },
  props: ["content_id"],
  watch: {
    content_id() {
      http.get(`/camp/content/${this.content_id}`).then((res) => {
        this.camp = res.data;
        this.getCampFavorites();
      });
    },
  },
  methods: {
    addFavorites() {
      const favorites = {
        content_id: this.camp.content_id,
        user_idx: this.$store.state.userIdx,
      };
      http.post("/favorites", favorites).then(() => {
        this.reloadPage();
      });
    },
    removeFavorites() {
      http
        .delete(
          `/favorites/${this.camp.content_id}/${this.$store.state.userIdx}`
        )
        .then(() => {
          this.reloadPage();
        });
    },
    getCampFavorites() {
      http
        .get(`/favorites/${this.camp.content_id}/${this.$store.state.userIdx}`)
        .then((res) => {
          this.campFavoritesFlag = res.data;
        });
    },
    reloadPage() {
      http.get(`/camp/content/${this.content_id}`).then((res) => {
        this.camp = res.data;
        this.getCampFavorites();
      });
    },
  },
};
</script>

<style scoped>
table {
  margin-left: auto;
  margin-right: auto;
}
td,
th {
  width: 500px;
}
.overview {
  float: right;
  margin-right: 100px;
  width: 700px;
}
img {
  float: left;
  margin-left: 100px;
}
</style>
