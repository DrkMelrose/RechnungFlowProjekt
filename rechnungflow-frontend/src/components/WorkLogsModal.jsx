import React, { useEffect, useState } from "react";
import { X } from "lucide-react";

const getToday = () => {
    const today = new Date();

    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, "0");
    const day = String(today.getDate()).padStart(2, "0");

    return `${year}-${month}-${day}`;
};

const getEmptyFormData = () => ({
    employeeId: "",
    cleaningObjectId: "",
    workDate: getToday(),
    hours: "",
    description: ""
});

export default function WorkLogsModal({
                                          isOpen,
                                          onClose,
                                          onSave,
                                          workLog,
                                          employees = [],
                                          cleaningObjects = []
                                      }) {
    const [formData, setFormData] = useState(getEmptyFormData());
    const [error, setError] = useState("");

    useEffect(() => {
        if (!isOpen) {
            return;
        }

        if (workLog) {
            setFormData({
                employeeId:
                    workLog.employee?.id?.toString() ?? "",

                cleaningObjectId:
                    workLog.cleaningObject?.id?.toString()
                    ?? workLog.object?.id?.toString()
                    ?? "",

                workDate:
                    workLog.date
                    ?? workLog.workDate
                    ?? "",

                hours:
                    workLog.hours?.toString() ?? "",

                description:
                    workLog.description ?? ""
            });
        } else {
            setFormData(getEmptyFormData());
        }

        setError("");
    }, [isOpen, workLog]);

    const handleChange = event => {
        const { name, value } = event.target;

        setFormData(previousFormData => ({
            ...previousFormData,
            [name]: value
        }));
    };

    const handleSubmit = async event => {
        event.preventDefault();
        setError("");

        if (!formData.employeeId) {
            setError("Please choose a worker");
            return;
        }

        if (!formData.cleaningObjectId) {
            setError("Please choose a cleaning object");
            return;
        }

        if (!formData.workDate) {
            setError("Please choose a date");
            return;
        }

        if (!formData.hours || Number(formData.hours) <= 0) {
            setError("Please choose a correct time of work");
            return;
        }

        const workLogToSave = {
            employeeId: Number(formData.employeeId),
            cleaningObjectId: Number(formData.cleaningObjectId),
            workDate: formData.workDate,
            hours: Number(formData.hours),
            description: formData.description.trim()
        };

        try {
            await onSave(workLogToSave);
        } catch (saveError) {
            console.error(
                "Error by loading the worklog",
                saveError
            );

            setError(
                saveError.message
                || "The worklog coud not be saved"
            );
        }
    };

    const getEmployeeName = employee => {
        if (employee.name) {
            return employee.name;
        }

        const fullName = [
            employee.firstName,
            employee.lastName
        ]
            .filter(Boolean)
            .join(" ");

        return fullName || `Employee ${employee.id}`;
    };

    const getCleaningObjectName = cleaningObject => {
        const objectName =
            cleaningObject.name
            || `Object ${cleaningObject.id}`;

        const clientName = cleaningObject.client?.name ||
            "";

        const address =
            cleaningObject.address || "";

        return [objectName, clientName, address]
            .filter(Boolean)
        .join(" - ");
    };

    if (!isOpen) {
        return null;
    }

    return (
        <div
            className="fixed inset-0 z-50 flex items-center justify-center bg-black/40 p-4"
            onMouseDown={onClose}
        >
            <div
                className="w-full max-w-lg rounded-2xl bg-white shadow-xl"
                onMouseDown={(event) => event.stopPropagation()}
            >
                {/* Header */}
                <div className="flex items-center justify-between border-b border-slate-200 px-6 py-4">
                    <div>
                        <h2 className="text-xl font-semibold text-slate-900">
                            {workLog
                                ? "Edit Work Log"
                                : "Create Work Log"}
                        </h2>

                        <p className="mt-1 text-sm text-slate-500">
                            Enter working time and cleaning details.
                        </p>
                    </div>

                    <button
                        type="button"
                        onClick={onClose}
                        className="rounded-lg p-2 text-slate-400 transition hover:bg-slate-100 hover:text-slate-700"
                        aria-label="Close modal"
                    >
                        <X size={20} />
                    </button>
                </div>

                <form
                    onSubmit={handleSubmit}
                    className="space-y-5 p-6"
                >
                    {/* Employee */}
                    <div>
                        <label
                            htmlFor="employeeId"
                            className="mb-2 block text-sm font-medium text-slate-700"
                        >
                            Employee
                        </label>

                        <select
                            id="employeeId"
                            name="employeeId"
                            value={formData.employeeId}
                            onChange={handleChange}
                            required
                            className="w-full rounded-xl border border-slate-200 bg-white px-4 py-2.5 text-sm outline-none transition focus:border-blue-400 focus:ring-2 focus:ring-blue-100"
                        >
                            <option value="">
                                Choose the employee
                            </option>

                            {employees.map((employee) => (
                                <option
                                    key={employee.id}
                                    value={employee.id}
                                >
                                    {getEmployeeName(employee)}
                                </option>
                            ))}
                        </select>
                    </div>

                    {/* Cleaning Object */}
                    <div>
                        <label
                            htmlFor="cleaningObjectId"
                            className="mb-2 block text-sm font-medium text-slate-700"
                        >
                            Cleaning object
                        </label>

                        <select
                            id="cleaningObjectId"
                            name="cleaningObjectId"
                            value={formData.cleaningObjectId}
                            onChange={handleChange}
                            required
                            className="w-full rounded-xl border border-slate-200 bg-white px-4 py-2.5 text-sm outline-none transition focus:border-blue-400 focus:ring-2 focus:ring-blue-100"
                        >
                            <option value="">
                                Choose the object
                            </option>

                            {cleaningObjects
                                .filter(
                                    (cleaningObject) =>
                                        cleaningObject.active !== false ||
                                        cleaningObject.id ===
                                        workLog?.cleaningObject?.id
                                )
                                .map((cleaningObject) => (
                                    <option
                                        key={cleaningObject.id}
                                        value={cleaningObject.id}
                                    >
                                        {getCleaningObjectName(
                                            cleaningObject
                                        )}
                                    </option>
                                ))}
                        </select>
                    </div>

                    {/* Date + Hours */}
                    <div className="grid grid-cols-1 gap-4 sm:grid-cols-2">
                        <div>
                            <label
                                htmlFor="workDate"
                                className="mb-2 block text-sm font-medium text-slate-700"
                            >
                                Working date
                            </label>

                            <input
                                id="workDate"
                                name="workDate"
                                type="date"
                                value={formData.workDate}
                                onChange={handleChange}
                                required
                                className="w-full rounded-xl border border-slate-200 px-4 py-2.5 text-sm outline-none transition focus:border-blue-400 focus:ring-2 focus:ring-blue-100"
                            />
                        </div>

                        <div>
                            <label
                                htmlFor="hours"
                                className="mb-2 block text-sm font-medium text-slate-700"
                            >
                                Working hours
                            </label>

                            <input
                                id="hours"
                                name="hours"
                                type="number"
                                min="0.25"
                                step="0.25"
                                value={formData.hours}
                                onChange={handleChange}
                                placeholder="e.g. 5.5"
                                required
                                className="w-full rounded-xl border border-slate-200 px-4 py-2.5 text-sm outline-none transition focus:border-blue-400 focus:ring-2 focus:ring-blue-100"
                            />
                        </div>
                    </div>

                    {/* Description */}
                    <div>
                        <label
                            htmlFor="description"
                            className="mb-2 block text-sm font-medium text-slate-700"
                        >
                            Description
                        </label>

                        <textarea
                            id="description"
                            name="description"
                            value={formData.description}
                            onChange={handleChange}
                            placeholder="Which cleaning tasks were completed?"
                            rows={4}
                            className="w-full resize-none rounded-xl border border-slate-200 px-4 py-3 text-sm outline-none transition focus:border-blue-400 focus:ring-2 focus:ring-blue-100"
                        />
                    </div>

                    {/* Error */}
                    {error && (
                        <div className="rounded-xl border border-red-200 bg-red-50 px-4 py-3 text-sm text-red-700">
                            {error}
                        </div>
                    )}

                    {/* Actions */}
                    <div className="flex justify-end gap-3 border-t border-slate-100 pt-5">
                        <button
                            type="button"
                            onClick={onClose}
                            className="rounded-xl border border-slate-200 px-4 py-2.5 text-sm font-medium text-slate-700 transition hover:bg-slate-50"
                        >
                            Cancel
                        </button>

                        <button
                            type="submit"
                            className="rounded-xl bg-blue-600 px-4 py-2.5 text-sm font-medium text-white transition hover:bg-blue-700"
                        >
                            {workLog
                                ? "Save changes"
                                : "Create Work Log"}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );

}