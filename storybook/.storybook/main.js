module.exports = {
  stories: [
    "../stories/**/*.stories.mdx",
    "../stories/**/*.stories.@(js|jsx|ts|tsx)",
  ],
  addons: [
    "@storybook/addon-docs",
    "@storybook/addon-links",
    "@storybook/addon-essentials",
    "storybook-react-i18next",
  ],
  features: {
    storyStoreV7: true,
    buildStoriesJson: true,
  },
  framework: {
    name: "@storybook/react-webpack5",
    options: {},
  },
};
