import React, { useState } from "react";
import { employees as initialEmployees} from "../data/mockData.js";
import { Plus, Search, Pencil, Trash2, UserRound } from "lucide-react";

export default function Employees() {
    const [employee, setEmployee] = useState(initialEmployees);
    const [isModalOpen, setIsModalOpen] = useState(false);

    const [searchTerm, setSearchTerm] = useState("");

    const filteredEmployees = employees.filter((employee) =>
        `${employee.firstName} ${employee.lastName}`
            .toLowerCase()
            .includes(searchTerm.toLowerCase())
    );

    return (
        <div className="space-y-6">
            {/* Header */}
            <div className="flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">
                <div>
                    <h1 className="text-2xl font-semibold text-slate-900">Employees</h1>
                    <p className="text-slate-500">
                        Manage all employees and their working roles.
                    </p>
                </div>

                <button onClick={()=> setIsModalOpen(true)} className="flex items-center justify-center gap-2 bg-blue-600 text-white px-4 py-2 rounded-xl text-sm font-medium hover:bg-blue-700 transition">
                    <Plus size={16} />
                    Add Employee
                </button>
                {isModalOpen && (
                    <EmployeeModal
                        onClick={()=> setIsModalOpen(false)}
                        onSave={handleSaveEmployee}
                    />
                )}
            </div>

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
                        onChange={(e) => setSearchTerm(e.target.value)}
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
                            <th className="text-left px-6 py-4 font-medium">Employee</th>
                            <th className="text-left px-6 py-4 font-medium">Email</th>
                            <th className="text-left px-6 py-4 font-medium">Phone</th>
                            <th className="text-left px-6 py-4 font-medium">Position</th>
                            <th className="text-left px-6 py-4 font-medium">Status</th>
                            <th className="text-right px-6 py-4 font-medium">Actions</th>
                        </tr>
                        </thead>

                        <tbody>
                        {filteredEmployees.map((employee) => (
                            <tr
                                key={employee.id}
                                className="border-t border-slate-100 hover:bg-slate-50 transition"
                            >
                                <td className="px-6 py-4">
                                    <div>
                                        <p className="font-semibold text-slate-900">
                                            {employee.firstName} {employee.lastName}
                                        </p>
                                        <p className="text-xs text-slate-500">
                                            Employee #{employee.id}
                                        </p>
                                    </div>
                                </td>

                                <td className="px-6 py-4 text-slate-600">
                                    {employee.email}
                                </td>

                                <td className="px-6 py-4 text-slate-600">
                                    {employee.phone}
                                </td>

                                <td className="px-6 py-4 text-slate-600">
                                    {employee.position}
                                </td>

                                <td className="px-6 py-4">
                    <span
                        className={`inline-flex px-3 py-1 rounded-full text-xs font-medium ${
                            employee.status === "Active"
                                ? "bg-green-50 text-green-700"
                                : "bg-slate-100 text-slate-500"
                        }`}
                    >
                      {employee.status}
                    </span>
                                </td>

                                <td className="px-6 py-4">
                                    <div className="flex justify-end gap-2">
                                        <button className="p-2 rounded-lg text-slate-500 hover:bg-slate-100 hover:text-slate-900">
                                            <Pencil size={16} />
                                        </button>

                                        <button className="p-2 rounded-lg text-slate-500 hover:bg-red-50 hover:text-red-600">
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
                                        {employee.firstName} {employee.lastName}
                                    </h3>

                                    <p className="text-sm text-slate-500 truncate">
                                        {employee.position}
                                    </p>
                                </div>

                                <span
                                    className={`shrink-0 px-2 py-1 rounded-full text-xs font-medium ${
                                        employee.status === "Active"
                                            ? "bg-green-50 text-green-700"
                                            : "bg-slate-100 text-slate-500"
                                    }`}
                                >
                  {employee.status}
                </span>
                            </div>

                            <div className="mt-4 grid grid-cols-1 gap-3 text-sm">
                                <div>
                                    <p className="text-slate-400">Email</p>
                                    <p className="text-slate-700 truncate">{employee.email}</p>
                                </div>

                                <div>
                                    <p className="text-slate-400">Phone</p>
                                    <p className="text-slate-700">{employee.phone}</p>
                                </div>
                            </div>

                            <div className="mt-4 flex justify-end gap-2">
                                <button className="p-2 rounded-lg text-slate-500 hover:bg-slate-100">
                                    <Pencil size={16} />
                                </button>

                                <button className="p-2 rounded-lg text-slate-500 hover:bg-red-50 hover:text-red-600">
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
            <p className="text-slate-900 font-medium">No employees found</p>
            <p className="text-sm text-slate-500 mt-1">
                Try changing your search term.
            </p>
        </div>
    );
}