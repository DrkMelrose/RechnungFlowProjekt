import React, { useEffect, useState } from "react";
import { Plus, Search, Pencil, Trash2, UserRound } from "lucide-react";
import EmployeeModal from "../components/EmployeeModal.jsx";

const API_URL = "http://localhost:8189/api/employees";

export default function Employees() {
    const [employees, setEmployees] = useState([]);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [editingEmployee, setEditingEmployee] = useState(null);
    const [searchTerm, setSearchTerm] = useState("");
    const [error, setError] = useState("");

    useEffect(() => {
        fetchEmployees();
    }, []);

    const fetchEmployees = async () => {
        try {
            setError("");

            const response = await fetch(API_URL);

            if (!response.ok) {
                throw new Error("Employees could not be loaded");
            }

            const data = await response.json();
            setEmployees(data);
        } catch (error) {
            console.error("Error loading employees:", error);
            setError("Employees could not be loaded.");
        }
    };

    const handleOpenCreateModal = () => {
        setEditingEmployee(null);
        setIsModalOpen(true);
    };

    const handleOpenEditModal = (employee) => {
        setEditingEmployee(employee);
        setIsModalOpen(true);
    };

    const handleCloseModal = () => {
        setEditingEmployee(null);
        setIsModalOpen(false);
    };

    const handleSaveEmployee = async (employeeData) => {
        try {
            setError("");

            const isEditing = editingEmployee !== null;

            const url = isEditing
                ? `${API_URL}/${editingEmployee.id}`
                : API_URL;

            const response = await fetch(url, {
                method: isEditing ? "PUT" : "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(employeeData),
            });

            if (!response.ok) {
                throw new Error("Employee could not be saved");
            }

            await fetchEmployees();
            handleCloseModal();
        } catch (error) {
            console.error("Error saving employee:", error);
            setError("Employee could not be saved.");
        }
    };

    const handleDeleteEmployee = async (id) => {
        const shouldDelete = window.confirm(
            "Do you really want to delete this employee?"
        );

        if (!shouldDelete) {
            return;
        }

        try {
            setError("");

            const response = await fetch(`${API_URL}/${id}`, {
                method: "DELETE",
            });

            if (!response.ok) {
                throw new Error("Employee could not be deleted");
            }

            setEmployees((currentEmployees) =>
                currentEmployees.filter((employee) => employee.id !== id)
            );
        } catch (error) {
            console.error("Error deleting employee:", error);
            setError("Employee could not be deleted.");
        }
    };

    const filteredEmployees = employees.filter((employee) => {
        const searchableText = `${employee.name ?? ""} ${
            employee.email ?? ""
        } ${employee.phone ?? ""}`;

        return searchableText
            .toLowerCase()
            .includes(searchTerm.toLowerCase());
    });

    return (
        <div className="space-y-6">
            {/* Header */}
            <div className="flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">
                <div>
                    <h1 className="text-2xl font-semibold text-slate-900">
                        Employees
                    </h1>

                    <p className="text-slate-500">
                        Manage all employees.
                    </p>
                </div>

                <button
                    onClick={handleOpenCreateModal}
                    className="flex items-center justify-center gap-2 bg-blue-600 text-white px-4 py-2 rounded-xl text-sm font-medium hover:bg-blue-700 transition"
                >
                    <Plus size={16} />
                    Add Employee
                </button>
            </div>

            {isModalOpen && (
                <EmployeeModal
                    employee={editingEmployee}
                    onClose={handleCloseModal}
                    onSave={handleSaveEmployee}
                />
            )}

            {error && (
                <div className="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-xl">
                    {error}
                </div>
            )}

            {/* Toolbar */}
            <div className="bg-white border border-slate-200 rounded-2xl p-4 flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">
                <div className="relative w-full max-w-sm">
                    <Search
                        size={18}
                        className="absolute left-3 top-1/2 -translate-y-1/2 text-slate-400"
                    />

                    <input
                        type="text"
                        placeholder="Search employees..."
                        value={searchTerm}
                        onChange={(event) => setSearchTerm(event.target.value)}
                        className="w-full pl-10 pr-4 py-2 border border-slate-200 rounded-xl text-sm outline-none focus:ring-2 focus:ring-blue-100 focus:border-blue-400"
                    />
                </div>

                <p className="text-sm text-slate-500">
                    {filteredEmployees.length} employees found
                </p>
            </div>

            {/* Desktop Table */}
            <div className="hidden md:block bg-white border border-slate-200 rounded-2xl overflow-hidden shadow-sm">
                {filteredEmployees.length > 0 ? (
                    <table className="w-full text-sm">
                        <thead className="bg-slate-50 text-slate-500">
                        <tr>
                            <th className="text-left px-6 py-4 font-medium">
                                Employee
                            </th>

                            <th className="text-left px-6 py-4 font-medium">
                                Email
                            </th>

                            <th className="text-left px-6 py-4 font-medium">
                                Phone
                            </th>

                            <th className="text-right px-6 py-4 font-medium">
                                Actions
                            </th>
                        </tr>
                        </thead>

                        <tbody>
                        {filteredEmployees.map((employee) => (
                            <tr
                                key={employee.id}
                                className="border-t border-slate-100 hover:bg-slate-50 transition"
                            >
                                <td className="px-6 py-4">
                                    <p className="font-semibold text-slate-900">
                                        {employee.name}
                                    </p>

                                    <p className="text-xs text-slate-500">
                                        Employee #{employee.id}
                                    </p>
                                </td>

                                <td className="px-6 py-4 text-slate-600">
                                    {employee.email}
                                </td>

                                <td className="px-6 py-4 text-slate-600">
                                    {employee.phone}
                                </td>

                                <td className="px-6 py-4">
                                    <div className="flex justify-end gap-2">
                                        <button
                                            onClick={() =>
                                                handleOpenEditModal(employee)
                                            }
                                            className="p-2 rounded-lg text-slate-500 hover:bg-slate-100 hover:text-slate-900"
                                        >
                                            <Pencil size={16} />
                                        </button>

                                        <button
                                            onClick={() =>
                                                handleDeleteEmployee(employee.id)
                                            }
                                            className="p-2 rounded-lg text-slate-500 hover:bg-red-50 hover:text-red-600"
                                        >
                                            <Trash2 size={16} />
                                        </button>
                                    </div>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                ) : (
                    <EmptyState />
                )}
            </div>

            {/* Mobile Cards */}
            <div className="md:hidden space-y-4">
                {filteredEmployees.length > 0 ? (
                    filteredEmployees.map((employee) => (
                        <div
                            key={employee.id}
                            className="bg-white border border-slate-200 rounded-2xl p-4 shadow-sm"
                        >
                            <div className="flex items-start gap-3">
                                <div className="w-10 h-10 rounded-xl bg-blue-50 text-blue-600 flex items-center justify-center shrink-0">
                                    <UserRound size={20} />
                                </div>

                                <div className="min-w-0 flex-1">
                                    <h3 className="font-semibold text-slate-900 truncate">
                                        {employee.name}
                                    </h3>

                                    <p className="text-sm text-slate-500">
                                        Employee #{employee.id}
                                    </p>
                                </div>
                            </div>

                            <div className="mt-4 grid grid-cols-1 gap-3 text-sm">
                                <div>
                                    <p className="text-slate-400">Email</p>
                                    <p className="text-slate-700 truncate">
                                        {employee.email}
                                    </p>
                                </div>

                                <div>
                                    <p className="text-slate-400">Phone</p>
                                    <p className="text-slate-700">
                                        {employee.phone}
                                    </p>
                                </div>
                            </div>

                            <div className="mt-4 flex justify-end gap-2">
                                <button
                                    onClick={() =>
                                        handleOpenEditModal(employee)
                                    }
                                    className="p-2 rounded-lg text-slate-500 hover:bg-slate-100"
                                >
                                    <Pencil size={16} />
                                </button>

                                <button
                                    onClick={() =>
                                        handleDeleteEmployee(employee.id)
                                    }
                                    className="p-2 rounded-lg text-slate-500 hover:bg-red-50 hover:text-red-600"
                                >
                                    <Trash2 size={16} />
                                </button>
                            </div>
                        </div>
                    ))
                ) : (
                    <EmptyState />
                )}
            </div>
        </div>
    );
}

function EmptyState() {
    return (
        <div className="py-16 text-center">
            <p className="text-slate-900 font-medium">
                No employees found
            </p>

            <p className="text-sm text-slate-500 mt-1">
                Add a new employee or change your search term.
            </p>
        </div>
    );
}