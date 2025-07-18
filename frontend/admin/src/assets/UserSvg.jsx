import * as React from "react";
const UserSvg = (props) => (
  <svg
    width="20px"
    height="20px"
    viewBox="0 0 24 24"
    id="user"
    data-name="Flat Color"
    xmlns="http://www.w3.org/2000/svg"
    className="icon flat-color"
    {...props}
  >
    <path
      id="#0000"
      d="M21,20a2,2,0,0,1-2,2H5a2,2,0,0,1-2-2,6,6,0,0,1,6-6h6A6,6,0,0,1,21,20Zm-9-8A5,5,0,1,0,7,7,5,5,0,0,0,12,12Z"
      style={{
        fill: "rgb(255, 255, 255)",
      }}
    />
  </svg>
);
export default UserSvg;
