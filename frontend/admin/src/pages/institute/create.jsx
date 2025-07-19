import "../../styles/globals.css";
import Sidebar from "../../components/sidebar/sidebar";
import Section from "../../components/section/section";
import HeaderBar from "../../components/header_bar/header_bar";
import Form from "../../components/form/post";

import { useCreateInstitute } from "../../hooks/institute"; // using new central hook file

function post() {
  const {
    formData,
    handle_input_change,
    handle_submit,
    loading,
    error,
    fieldErrors,
  } = useCreateInstitute();

  return (
    <div className="row">
      <Sidebar />
      <Section>
        <HeaderBar />
        {loading && <p>Submitting...</p>}
        {error && <p style={{ color: "red" }}>Error: {error}</p>}
        <Form
          formData={formData}
          handle_input_change={handle_input_change}
          handle_submit={handle_submit}
          error={error}
          fieldErrors={fieldErrors}
        />
      </Section>
    </div>
  );
}

export default post;
