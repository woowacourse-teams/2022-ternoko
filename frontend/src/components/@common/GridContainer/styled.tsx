import styled, { css } from 'styled-components';

type GridContainerProps = {
  minSize: string;
  pt?: string;
  pr?: string;
  pb?: string;
  pl?: string;
};

const GridContainer = styled.div<GridContainerProps>`
  display: grid;
  grid-template-columns: ${({ minSize }) => `repeat(auto-fill, minmax(${minSize}, 1fr))}`};
  grid-gap: 3rem;

  ${({ pt, pr, pb, pl }) => css`
    padding-top: ${pt ?? ''};
    padding-right: ${pr ?? ''};
    padding-bottom: ${pb ?? ''};
    padding-left: ${pl ?? ''};
  `}
`;

export default GridContainer;
