module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}", // Your React files
    "./node_modules/flowbite-react/**/*.{js,jsx,ts,tsx}" // Include flowbite
  ],
  theme: {
    extend: {},
  },
  plugins: [
    require("flowbite/plugin"), // Add Flowbite plugin
  ],
};
