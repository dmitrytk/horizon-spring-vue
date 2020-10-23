<template>
  <b-navbar toggleable="lg" type="dark" variant="dark">
    <b-navbar-brand to="/">Horizon</b-navbar-brand>
    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
    <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav>
        <b-nav-item to="/">Home</b-nav-item>
        <b-nav-item to="/about">About</b-nav-item>
        <b-nav-item to="/fields">Fields</b-nav-item>
        <b-nav-item-dropdown>
          <template v-slot:button-content>
            Tools
          </template>
          <b-dropdown-item to="/csv_to_sql">SQL generator</b-dropdown-item>
          <b-dropdown-item to="/coordinate_converter">Coordinate converter</b-dropdown-item>
        </b-nav-item-dropdown>
      </b-navbar-nav>

      <b-navbar-nav class="ml-auto">
        <b-nav-form>
          <b-form-input class="mr-sm-2" placeholder="Search" size="sm"></b-form-input>
          <b-button class="my-2 my-sm-0" size="sm" type="submit">Search</b-button>
        </b-nav-form>

        <b-nav-item-dropdown right>
          <template v-slot:button-content>
            <em>User</em>
          </template>
          <b-dropdown-item href="#">Profile</b-dropdown-item>
          <b-dropdown-item href="#">Sign Out</b-dropdown-item>
        </b-nav-item-dropdown>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</template>

<script>
export default {
  name: 'Navbar',
};
</script>
