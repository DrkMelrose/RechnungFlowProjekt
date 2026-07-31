import React, { useEffect, useState } from "react";
import { X } from "lucide-react";

export default function EmployeeModal({
                                          employee,
                                          onClose,
                                          onSave
                                      }) {
    const [formData, setFormData] = useState({
        name: "",
        phone: "",
        email: ""
    });

    useEffect(() => {
        if (employee) {
            setFormData({
                name: employee.name || "",
                phone: employee.phone || "",
                email: employee.email || ""
            });
        } else {
            setFormData({
                name: "",
                phone: "",
                email: ""
            });
        }
    }, [employee]);

    const handleChange = (event) => {
        const { name, value } = event.target;

        setFormData((currentData) => ({
            ...currentData,
            [name]: value
        }));
    };

    const handleSubmit = (event) => {
        event.preventDefault();

        onSave({
            name: formData.name.trim(),
            phone: formData.phone.trim(),
            email: formData.email.trim()
        });
    };

    return (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/40 px-4">
            <div className="w-full max-w-md rounded-2xl bg-white p-6 shadow-xl">
                <div className="flex items-center justify-between">
                    <div>
                        <h2 className="text-xl font-semibold text-slate-900">
                            {employee ? "Edit Employee" : "Add Employee"}
                        </h2>

                        <p className="mt-1 text-sm text-slate-500">
                            Enter the employee information.
                        </p>
                    </div>

                    <button
                        type="button"
                        onClick={onClose}
                        className="rounded-lg p-2 text-slate-400 hover:bg-slate-100 hover:text-slate-700"
                    >
                        <X size={20} />
                    </button>
                </div>

                <form onSubmit={handleSubmit} className="mt-6 space-y-4">
                    <div>
                        <label
                            htmlFor="name"
                            className="mb-1 block text-sm font-medium text-slate-700"
                        >
                            Name
                        </label>

                        <input
                            id="name"
                            name="name"
                            type="text"
                            value={formData.name}
                            onChange={handleChange}
                            required
                            placeholder="Employee name"
                            className="w-full rounded-xl border border-slate-200 px-4 py-2.5 text-sm outline-none focus:border-blue-400 focus:ring-2 focus:ring-blue-100"
                        />
                    </div>

                    <div>
                        <label
                            htmlFor="email"
                            className="mb-1 block text-sm font-medium text-slate-700"
                        >
                            Email
                        </label>

                        <input
                            id="email"
                            name="email"
                            type="email"
                            value={formData.email}
                            onChange={handleChange}
                            required
                            placeholder="employee@example.com"
                            className="w-full rounded-xl border border-slate-200 px-4 py-2.5 text-sm outline-none focus:border-blue-400 focus:ring-2 focus:ring-blue-100"
                        />
                    </div>

                    <div>
                        <label
                            htmlFor="phone"
                            className="mb-1 block text-sm font-medium text-slate-700"
                        >
                            Phone
                        </label>

                        <input
                            id="phone"
                            name="phone"
                            type="text"
                            value={formData.phone}
                            onChange={handleChange}
                            required
                            placeholder="+49 123 456789"
                            className="w-full rounded-xl border border-slate-200 px-4 py-2.5 text-sm outline-none focus:border-blue-400 focus:ring-2 focus:ring-blue-100"
                        />
                    </div>

                    <div className="flex justify-end gap-3 pt-4">
                        <button
                            type="button"
                            onClick={onClose}
                            className="rounded-xl border border-slate-200 px-4 py-2 text-sm font-medium text-slate-600 hover:bg-slate-50"
                        >
                            Cancel
                        </button>

                        <button
                            type="submit"
                            className="rounded-xl bg-blue-600 px-4 py-2 text-sm font-medium text-white hover:bg-blue-700"
                        >
                            {employee ? "Save Changes" : "Create Employee"}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}