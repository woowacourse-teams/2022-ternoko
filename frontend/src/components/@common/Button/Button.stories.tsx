import { Story } from '@storybook/react';

import Button, { ButtonProps } from './styled';

export default {
  title: 'components/@common/Button',
  component: Button,
};

const Template = (args: ButtonProps) => <Button {...args}>안녕하세요</Button>;

export const Basic: Story<ButtonProps> = Template.bind({});

export const White: Story<ButtonProps> = Template.bind({});

White.args = {
  white: true,
};

export const Orange: Story<ButtonProps> = Template.bind({});

Orange.args = {
  orange: true,
};

export const Home: Story<ButtonProps> = Template.bind({});

Home.args = {
  home: true,
};
