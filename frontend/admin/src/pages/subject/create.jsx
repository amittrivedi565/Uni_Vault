import "../../styles/globals.css"
import Sidebar from "../../components/sidebar/sidebar";
import Section from "../../components/section/section"
import HeaderBar from "../../components/header_bar/header_bar"
import Form from "../../components/form/post"

import { useCreateSubject } from "../../hooks/use_subject"

function create() {
    const { formData, handle_input_change, handle_submit, error, fieldErrors } = useCreateSubject();
    const subjectFields = [
        { name: "name", label: "Name", required: true },
        { name: "shortname", label: "Shortname", required: true },
        { name: "code", label: "Code", required: true },
        { name: "description", label: "Description" },
    ];
    return (<>
        <div className="row">
            <Sidebar />
            <Section>
                <HeaderBar />
                {error && <p style={{ color: "red" }}>Error: {error}</p>}
                <Form
                    formData={formData}
                    handle_input_change={handle_input_change}
                    handle_submit={handle_submit}
                    error={error}
                    fieldErrors={fieldErrors}
                    fields={subjectFields}
                />
            </Section>
        </div>
    </>)
}

export default create;