import TestingComponent from "../TestingComponent";
import { render, screen} from "@testing-library/react";

test("Testing Component renders successfullty", () => {
  render(<TestingComponent />);
  const title = screen.getByTestId("component-title");
  expect(title).toBeInTheDocument()
});
