import Header from '../Header';

export default {
  title: 'components/@common/Header',
  component: Header,
};

const Template = (args) => <Header {...args} />;

export const Basic = Template.bind({});

Basic.args = {};
