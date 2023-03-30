import { CircularProgress } from "@chakra-ui/react";
import "./Loader.scss";

export default function Loader() {
  return (
    <div className="loader">
      <CircularProgress isIndeterminate color="#4B9FE1" />
    </div>
  );
}
