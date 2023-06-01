module.exports = {
  stories: [
    "../d2/**/*.stories.mdx",
    "../d2/**/*.stories.@(js|jsx|ts|tsx)",
  ],
  addons: [
    {
      name: "@storybook/addon-docs",
      options: {
        configureJSX: true,
        transcludeMarkdown: true,
      },
    },
    "@storybook/addon-links",
    "@storybook/addon-essentials",
    "storybook-react-i18next",
  ],
  features: {
    emotionAlias: false,
    storyStoreV7: true,
  },
};
