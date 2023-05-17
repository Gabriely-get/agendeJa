import axios from "axios";

export default function useRegisterPortifolio() {
  const apiUrl = process.env.REACT_APP_API_AGENDEJA_AWS;

  async function registerPortifolio({
    categoryId,
    companyBranchId,
    subcategories,
  }) {
    let url = `${apiUrl}:5000/agenda/portfolio/`;

    const response = await axios.post(url, {
      categoryId: categoryId,
      companyBranchId: companyBranchId,
      subcategories: subcategories,
    });
    return response.data;
  }

  return {
    registerPortifolio,
  };
}
