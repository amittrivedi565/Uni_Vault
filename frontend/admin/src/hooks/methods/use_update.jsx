import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

const use_update = (fetchByIdFn, updateFn, defaultFormData, navigateTo) => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [formData, setFormData] = useState(defaultFormData);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetch = async () => {
      try {
        const data = await fetchByIdFn(id);
        setFormData({
          ...defaultFormData,
          ...data,
        });
      } catch (err) {
        setError(err.message || "Failed to load data.");
      } finally {
        setLoading(false);
      }
    };
    if (id) fetch();
  }, [id]);

  const handle_input_change = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => {
      if (prev[name] === value) return prev;
      return { ...prev, [name]: value };
    });
  };

  const handle_submit = async (e) => {
    e.preventDefault();
    try {
      const result = await updateFn(id, formData);
      if (result) {
        alert("Updated successfully!");
        navigate(navigateTo || 0); // navigate to path or reload
      } else {
        alert("Update failed.");
      }
    } catch (err) {
      alert("Failed to update: " + (err.message || "Unknown error"));
    }
  };

  return {
    formData,
    handle_input_change,
    handle_submit,
    loading,
    error,
  };
};

export default use_update;
