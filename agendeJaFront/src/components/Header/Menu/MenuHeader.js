import { RiArrowDownSFill, RiArrowUpSFill } from "react-icons/ri";
import { Menu, MenuButton, MenuList, MenuItem } from "@chakra-ui/react";
import "./MenuHeader.scss";
import { Link } from "react-router-dom";
import { useDispatch } from "react-redux";
import { logout } from "../../../redux/userSlice";
import { removeUser } from "../../../redux/userSliceDados";

export default function MenuHeader() {
  const dispatch = useDispatch();
  return (
    <Menu>
      {({ isOpen }) => (
        <>
          <MenuButton isActive={isOpen} as="Button">
            {isOpen ? <RiArrowUpSFill /> : <RiArrowDownSFill />}
          </MenuButton>
          <MenuList>
            <MenuItem>
              <Link to="/minhaconta" className="anchor">
                Minha conta
              </Link>
            </MenuItem>
            <MenuItem
              onClick={() => {
                dispatch(logout());
                dispatch(removeUser());
              }}
            >
              Sair
            </MenuItem>
          </MenuList>
        </>
      )}
    </Menu>
  );
}
