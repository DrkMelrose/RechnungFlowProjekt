import React, { useState } from "react";
import { X } from "lucide-react";

export default function ClientModal({ client, onClose, onSave }) {
    const [formData, setFormData] = useState({
        companyName: client?.companyName || "",
        contactPerson: client?.contactPerson || "",
        email: client?.email ||"",
        phone: client?.phone || "",
    });

    function handleChange(e) {
        const { name, value } = e.target;

        setFormData((prev) => ({
            ...prev,
            [name]: value,
        }));
    }

    function handleSubmit(e) {
        e.preventDefault();
        onSave(formData);
    }


    return (
        <div className="fixed inset-0 z-50 bg-black/40 flex items-center justify-center p-4">
            <div className="bg-white w-full max-w-lg rounded-2xl shadow-xl">
                <div className="flex items-center justify-between border-b border-slate-200 p-5">
                    <div>
                        <h2 className="text-lg font-semibold text-slate-900">
                        </h2>
                        <p className="text-sm text-slate-500">
                            {client? "Change the information about client" : "Create a new client record."}
                        </p>
                    </div>

                    <button
                        onClick={onClose}
                        className="p-2 rounded-lg text-slate-500 hover:bg-slate-100"
                    >
                        <X size={18} />
                    </button>
                </div>

                <form onSubmit={handleSubmit} className="p-5 space-y-4">
                    <Input
                        label="Company Name"
                        name="companyName"
                        value={formData.companyName}
                        onChange={handleChange}
                    />

                    <Input
                        label="Contact Person"
                        name="contactPerson"
                        value={formData.contactPerson}
                        onChange={handleChange}
                    />

                    <Input
                        label="Email"
                        name="email"
                        value={formData.email}
                        onChange={handleChange}
                    />

                    <Input
                        label="Phone"
                        name="phone"
                        value={formData.phone}
                        onChange={handleChange}
                    />

                    <div className="flex justify-end gap-3 pt-4">
                        <button
                            type="button"
                            onClick={onClose}
                            className="px-4 py-2 rounded-xl border border-slate-200 text-sm font-medium text-slate-600 hover:bg-slate-50"
                        >
                            Cancel
                        </button>

                        <button
                            type="submit"
                            className="px-4 py-2 rounded-xl bg-blue-600 text-white text-sm font-medium hover:bg-blue-700"
                        >
                            {client ? "Save Changes" : "Save Client"}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}

function Input({ label, name, value, onChange }) {
    return (
        <div>
            <label className="block text-sm font-medium text-slate-700 mb-2">
                {label}
            </label>

            <input
                name={name}
                value={value}
                onChange={onChange}
                type="text"
                className="w-full px-4 py-2 border border-slate-200 rounded-xl text-sm outline-none focus:ring-2 focus:ring-blue-100 focus:border-blue-400"
            />
        </div>
    );
}