import { configureStore } from "@reduxjs/toolkit";
import userReducer from "./userSlice";
import userReducerDados from "./userSliceDados";

export default configureStore({
  reducer: {
    user: userReducer,
    userDados: userReducerDados,
  },
});
