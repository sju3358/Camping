<template>
  <div id="app">
    <nav>
      <router-link to="/camping">Camping</router-link> |
      <router-link to="/board">FreeBoard</router-link>
      |
      <router-link to="/mypage">MyPage</router-link>
    </nav>
    <img alt="Vue logo" src="@/assets/logo.png" />
    <router-view />
  </div>
</template>

<style>
#app {
  font-family: 휴먼엑스포, Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  font-family: 궁서;
  padding: 30px;
}
button {
  font-family: 휴먼엑스포;
}
nav a {
  text-decoration: none;
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
