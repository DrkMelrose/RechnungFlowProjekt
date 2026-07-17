import { useEffect, useState } from "react";

export default function CleaningObjectModal({
                                                isOpen,
                                                onClose,
                                                onSave,
                                                editingCleaningObject,
                                                clients,
                                            }) {
    const [formData, setFormData] = useState({
        clientId: "",
        name: "",
        address: "",
        hourlyRate: "",
        fixedMonthlyPrice: "",
        active: true,
    });

    useEffect(() => {
        if (editingCleaningObject) {
            setFormData({
                clientId: editingCleaningObject.client?.id || "",
                name: editingCleaningObject.name || "",
                address: editingCleaningObject.address || "",
                hourlyRate: editingCleaningObject.hourlyRate || "",
                fixedMonthlyPrice: editingCleaningObject.fixedMonthlyPrice || "",
                active: editingCleaningObject.active ?? true,
            });
        } else {
            setFormData({
                clientId: "",
                name: "",
                address: "",
                hourlyRate: "",
                fixedMonthlyPrice: "",
                active: true,
            });
        }
    }, [editingCleaningObject, isOpen]);

    function handleChange(e) {
        const { name, value, type, checked } = e.target;

        setFormData((prev) => ({
            ...prev,
            [name]: type === "checkbox" ? checked : value,
        }));
    }

    function handleSubmit(e) {
        e.preventDefault();

        const objectToSave={
            name: formData.name.trim(),
            address: formData.address.trim(),
            hourlyRate: Number(formData.hourlyRate),
            fixedMonthlyPrice: Number(formData.fixedMonthlyPrice),
            active: formData.active,
            client: {
                id: Number(formData.clientId),
            },
        };
        console.log("Sending object", objectToSave);
        onSave(objectToSave);
    }

    if (!isOpen) return null;
    console.log("Clients is modal: ", clients);

    return (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50">
            <div className="bg-white rounded-xl shadow-xl p-6 w-full max-w-lg">
                <h2 className="text-xl font-bold mb-6">
                    {editingCleaningObject ? "Edit Cleaning Object" : "New Cleaning Object"}
                </h2>

                <form onSubmit={handleSubmit} className="space-y-4">
                    <select
                        name="clientId"
                        value={formData.clientId}
                        onChange={handleChange}
                        className="w-full border rounded-lg p-2"
                        required
                    >
                        <option value="">Select client</option>
                        {clients.map((client) => (
                            <option key={client.id} value={client.id}>
                                {client.name || client.companyName || `Client ${client.id}`}
                            </option>
                        ))}
                    </select>

                    <input
                        name="name"
                        placeholder="Object name"
                        value={formData.name}
                        onChange={handleChange}
                        className="w-full border rounded-lg p-2"
                        required
                    />

                    <input
                        name="address"
                        placeholder="Address"
                        value={formData.address}
                        onChange={handleChange}
                        className="w-full border rounded-lg p-2"
                    />

                    <input
                        type="number"
                        name="hourlyRate"
                        placeholder="Hourly rate"
                        value={formData.hourlyRate}
                        onChange={handleChange}
                        className="w-full border rounded-lg p-2"
                    />

                    <input
                        type="number"
                        name="fixedMonthlyPrice"
                        placeholder="Fixed monthly price"
                        value={formData.fixedMonthlyPrice}
                        onChange={handleChange}
                        className="w-full border rounded-lg p-2"
                    />

                    <label className="flex items-center gap-2">
                        <input
                            type="checkbox"
                            name="active"
                            checked={formData.active}
                            onChange={handleChange}
                        />
                        Active
                    </label>

                    <div className="flex justify-end gap-3 pt-4">
                        <button type="button" onClick={onClose} className="px-4 py-2 rounded-lg border">
                            Cancel
                        </button>

                        <button  type="submit" className="px-4 py-2 rounded-lg bg-blue-600 text-white">
                            {editingCleaningObject ? "Save changes" : "Create"}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}
